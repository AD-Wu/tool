package com.x.commons.socket.core;

import io.netty.handler.timeout.IdleStateEvent;

public interface ISocketListener {
    
    void active(SocketChannel channel) throws Exception;
    
    void inActive(SocketChannel channel) throws Exception;
    
    void receive(SocketChannel channel, byte[] data)throws  Exception;
    
    void timeout(SocketChannel channel, IdleStateEvent event)throws  Exception;
    
    void error(SocketChannel channel, Throwable cause)throws Exception;
    
}
