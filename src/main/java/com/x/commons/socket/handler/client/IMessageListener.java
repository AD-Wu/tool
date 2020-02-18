package com.x.commons.socket.handler.client;

import io.netty.channel.ChannelHandlerContext;

/**
 * @Desc TODO
 * @Date 2020-02-18 21:18
 * @Author AD
 */
public interface IMessageListener {
    
    void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception;
    
}
