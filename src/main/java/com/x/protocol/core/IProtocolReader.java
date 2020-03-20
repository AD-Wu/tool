package com.x.protocol.core;

import com.x.protocol.network.interfaces.INetworkIO;

/**
 * @Desc
 * @Date 2020-03-20 21:52
 * @Author AD
 */
public interface IProtocolReader {
    
    void init(IProtocol prtc, ChannelInfo info, INetworkIO io) throws Exception;
    
    boolean nextPackage() throws Exception;
    
    ChannelData outputData() throws Exception;
    
    void reset();
    
}
