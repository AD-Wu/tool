package com.x.protocol.network.factory.seria;

import com.x.protocol.network.interfaces.INetworkOutput;

public interface ISerialOutput extends INetworkOutput {
    boolean send(byte[] bytes);
}
