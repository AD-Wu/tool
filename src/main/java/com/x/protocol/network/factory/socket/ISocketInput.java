package com.x.protocol.network.factory.socket;

import com.x.protocol.network.interfaces.INetworkInput;

/**
 * @Desc TODO
 * @Date 2020-03-02 00:11
 * @Author AD
 */
public interface ISocketInput extends INetworkInput {
    
    int available();
    
    int read();
    
    int read(byte[] buffer);
    
    void reset();
    
}
