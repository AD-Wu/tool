package com.x.commons.socket.proxy;

/**
 * @Desc TODO
 * @Date 2020-04-04 21:13
 * @Author AD
 */
public interface ISocketManager {
    
    void sendFromClient(String client, byte[] data, long seq);
    
    void sendToClient(String client, byte[] data, long seq);
    
}
