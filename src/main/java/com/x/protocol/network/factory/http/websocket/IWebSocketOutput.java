package com.x.protocol.network.factory.http.websocket;

import com.x.protocol.network.interfaces.INetworkOutput;

import java.nio.ByteBuffer;

/**
 * @Desc TODO
 * @Date 2020-03-07 20:52
 * @Author AD
 */
public interface IWebSocketOutput extends INetworkOutput {
    
    boolean send(String msg);
    
    boolean send(byte[] bytes);
    
    boolean send(ByteBuffer buffer);
    
}
