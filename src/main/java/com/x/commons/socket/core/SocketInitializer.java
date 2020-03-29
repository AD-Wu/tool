package com.x.commons.socket.core;

import com.x.commons.socket.bean.SocketConfig;
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

import java.util.List;
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
        
        private final ISocketListener listener;
        
        private SocketHandler(ISocketListener listener) {
            this.listener = listener;
        }
        
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            listener.active(new XSocketChannel(ctx, serializer));
        }
        
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (SocketInitializer.this.serializer == null) {
                if (msg instanceof ByteBuf) {
                    ByteBuf buf = (ByteBuf) msg;
                    listener.receive(new XSocketChannel(ctx, serializer), buf);
                }
            } else {
                listener.receive(new XSocketChannel(ctx, serializer), msg);
            }
        }
        
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            listener.inActive(new XSocketChannel(ctx, serializer));
        }
        
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if (IdleState.ALL_IDLE == event.state()) {
                    listener.timeout(new XSocketChannel(ctx, serializer), event);
                }
            }
        }
        
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            listener.error(new XSocketChannel(ctx, serializer), cause);
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
