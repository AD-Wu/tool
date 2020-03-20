package com.x.protocol.network.factory.custom;

import com.x.protocol.network.interfaces.INetworkOutput;

public interface ICustomOutput extends INetworkOutput {
    <T> boolean send(T t);
}
