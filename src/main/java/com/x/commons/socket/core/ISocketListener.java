package com.x.commons.socket.core;

import com.x.commons.socket.bean.XSocketChannel;
import io.netty.buffer.ByteBuf;
import io.netty.handler.timeout.IdleStateEvent;

public interface ISocketListener<T> {
    
    void active(XSocketChannel channel) throws Exception;
    
    void inActive(XSocketChannel channel) throws Exception;
    
    void receive(XSocketChannel channel, ByteBuf buf) throws Exception;
    
    void receive(XSocketChannel channel, T msg) throws Exception;
    
    void timeout(XSocketChannel channel, IdleStateEvent event) throws Exception;
    
    void error(XSocketChannel channel, Throwable cause) throws Exception;
    
}
