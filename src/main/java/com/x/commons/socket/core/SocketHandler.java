package com.x.commons.socket.core;

import com.x.commons.util.collection.XArrays;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/27 16:48
 */
public class SocketHandler extends SimpleChannelInboundHandler {

    private final ISocketListener listener;

    public SocketHandler(ISocketListener listener) {
        this.listener = listener;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        listener.active(new SocketChannel(ctx));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ByteBuf){
            ByteBuf buf = (ByteBuf) msg;
            byte[] data = new byte[buf.readableBytes()];
            buf.readBytes(data);
            byte[] copy = XArrays.copy(data);
            listener.receive(new SocketChannel(ctx), copy);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        listener.inActive(new SocketChannel(ctx));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(IdleState.ALL_IDLE == event.state()){
                listener.timeout(new SocketChannel(ctx), event);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        listener.error(new SocketChannel(ctx), cause);
    }
}
