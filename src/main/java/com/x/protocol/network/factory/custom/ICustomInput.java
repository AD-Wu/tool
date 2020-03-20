package com.x.protocol.network.factory.custom;

import com.x.protocol.network.interfaces.INetworkInput;

public interface ICustomInput extends INetworkInput {
    int available();

    <T> T read();

    void reset();
}
