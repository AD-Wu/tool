package com.x.commons.socket.core;

import com.x.commons.socket.bean.SocketConfig;

/**
 * @Date 2019-10-18 21:21
 * @Author AD
 */
public interface ISocket {
    
    boolean connect(SocketConfig config) throws Exception;
    
    void disconnect();
    
    boolean start(SocketConfig config) throws Exception;
    
    void stop();
    
}
