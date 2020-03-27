package com.x.commons.socket.core;

import com.x.commons.enums.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.net.SocketAddress;
import java.util.Arrays;

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
        System.out.println("channel active");
        Channel ch = ctx.channel();
        SocketAddress remote = ch.remoteAddress();
        SocketAddress local = ch.localAddress();
        System.out.println(remote);
        System.out.println(local);
        ByteBufAllocator alloc = ctx.alloc();
        System.out.println(alloc);
        ByteBuf buffer = alloc.buffer();
        System.out.println("server="+ Arrays.toString("哈哈".getBytes()));
        buffer.writeCharSequence("哈哈", Charsets.UTF8);
        ctx.writeAndFlush(buffer);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ByteBuf){
            ByteBuf buf = (ByteBuf) msg;
            byte[] data = new byte[buf.readableBytes()];
            buf.readBytes(data);
            System.out.println("==="+Arrays.toString(data));
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive");
        Channel ch = ctx.channel();
        SocketAddress remote = ch.remoteAddress();
        System.out.println(remote);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(IdleState.ALL_IDLE == event.state()){
                ctx.channel().close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
