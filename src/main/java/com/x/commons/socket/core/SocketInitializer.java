package com.x.commons.socket.core;

import com.x.commons.socket.bean.SocketConfig;
import com.x.commons.socket.util.Sockets;
import com.x.commons.util.bean.New;
import com.x.commons.util.log.Logs;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/27 16:37
 */
public class SocketInitializer extends ChannelInitializer<SocketChannel> {
    
    private final SocketConfig config;
    
    private final ISocketListener listener;
    
    private final ISocketSerializer serializer;
    
    public SocketInitializer(SocketConfig config,
            ISocketListener listener, ISocketSerializer serializer) {
        this.config = config;
        this.listener = listener;
        this.serializer = serializer;
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new IdleStateHandler(
                config.getReadTimeout(),
                config.getWriteTimeout(),
                config.getIdleTimeout(),
                TimeUnit.MINUTES
        ));
        if (serializer != null) {
            p.addLast(new SocketDecoder(serializer));
        }
        p.addLast(new SocketHandler(listener));
    }
    
    // ----------------------- 内部类 -----------------------
    
    /**
     * 处理器(消息监听器)
     */
    private class SocketHandler extends SimpleChannelInboundHandler {
        
        private final Logger logger = Logs.get(SocketHandler.class);
        
        private final ISocketListener listener;
        
        private final Map<String, XSocketChannel> sockets;
        
        private SocketHandler(ISocketListener listener) {
            this.listener = listener;
            this.sockets = New.concurrentMap();
        }
        
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            XSocketChannel channel = new XSocketChannel(ctx, serializer);
            sockets.put(channel.getSocketInfo().getRemoteAddress(), channel);
            listener.active(channel);
        }
        
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            String remote = Sockets.getRemoteAddress(ctx);
            if (!sockets.containsKey(remote)) {
                logger.warn("Receive illegal data,remote={}", remote);
            } else {
                XSocketChannel channel = sockets.get(remote);
                if (SocketInitializer.this.serializer == null) {
                    if (msg instanceof ByteBuf) {
                        ByteBuf buf = (ByteBuf) msg;
                        listener.receive(channel, buf, channel.getSeq());
                    }
                } else {
                    listener.receive(channel, msg, channel.getSeq());
                }
            }
        }
        
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            XSocketChannel channel = sockets.remove(Sockets.getRemoteAddress(ctx));
            listener.inActive(channel);
        }
        
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if (IdleState.ALL_IDLE == event.state()) {
                    XSocketChannel channel = sockets.get(Sockets.getRemoteAddress(ctx));
                    listener.timeout(channel, event);
                }
            }
        }
        
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            XSocketChannel channel = sockets.get(Sockets.getRemoteAddress(ctx));
            listener.error(channel, cause);
        }
        
    }
    
    /**
     * 反序列化
     */
    private static class SocketDecoder extends ByteToMessageDecoder {
        
        private final ISocketSerializer serializer;
        
        private SocketDecoder(ISocketSerializer serializer) {
            this.serializer = serializer;
        }
        
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
            List<Object> decodes = serializer.decode(buf);
            if (decodes != null && decodes.size() > 0) {
                list.addAll(decodes);
            }
        }
        
    }
    
}
