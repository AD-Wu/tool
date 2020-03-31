package com.x.commons.socket.core;

import com.x.commons.socket.bean.XSocketChannel;

/**
 * @Desc TODO
 * @Date 2020-03-31 23:02
 * @Author AD
 */
public interface ISocketManager {
    
    void addClient(String remote, XSocketChannel channel);
    
    void removeClient(String remote);
    
    void receive();
}
