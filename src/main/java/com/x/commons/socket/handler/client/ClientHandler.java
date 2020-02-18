package com.x.commons.socket.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Desc TODO
 * @Date 2020-02-18 21:17
 * @Author AD
 */
public class ClientHandler extends SimpleChannelInboundHandler {
    
    private final IMessageListener listener;
    public ClientHandler(IMessageListener listener){
        this.listener = listener;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        listener.messageReceived(ctx, msg);
    }
    
    
}
