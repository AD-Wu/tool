package com.x.commons.socket.handler.server;

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
 * @Date 2019-10-18 21:35
 * @Author AD
 */
public class ServerHandler extends SimpleChannelInboundHandler {

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
        System.out.println("server="+Arrays.toString("哈哈".getBytes()));
        buffer.writeCharSequence("哈哈", Charsets.UTF8);
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive");
        Channel ch = ctx.channel();
        SocketAddress remote = ch.remoteAddress();
        System.out.println(remote);
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
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(IdleState.ALL_IDLE == event.state()){
                ctx.channel().close();
            }
        }
    }
    
}
