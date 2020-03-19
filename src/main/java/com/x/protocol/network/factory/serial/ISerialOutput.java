package com.x.protocol.network.factory.serial;

import com.x.protocol.network.interfaces.INetworkOutput;

public interface ISerialOutput extends INetworkOutput {
    boolean send(byte[] bytes);
}
