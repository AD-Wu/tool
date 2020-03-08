package com.x.protocol.network.factory.http.websocket;

import com.x.protocol.network.interfaces.INetworkInput;

/**
 * @Desc TODO
 * @Date 2020-03-07 20:51
 * @Author AD
 */
public interface IWebSocketInput extends INetworkInput {
    
    byte[] getBytes();
    
    String getText();
    
}
