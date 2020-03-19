package com.x.protocol.network.factory.serial;

import com.x.protocol.network.interfaces.INetworkInput;

public interface ISerialInput extends INetworkInput {
    int available();
    int read();
    int read(byte[] bytes);
    void reset();
}
