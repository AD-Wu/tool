package com.x.protocol.network.factory.socket;

import com.x.protocol.network.interfaces.INetworkOutput;

/**
 * @Desc TODO
 * @Date 2020-03-02 00:12
 * @Author AD
 */
public interface ISocketOutput extends INetworkOutput {
    boolean send(byte[] bytes);
}
