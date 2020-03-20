package com.x.protocol.core;

import com.x.protocol.network.interfaces.INetworkIO;

/**
 * @Desc
 * @Date 2020-03-20 21:54
 * @Author AD
 */
public interface IProtocolSender {
    
    boolean send(IProtocol prtc, ChannelInfo info, INetworkIO io, ChannelData data) throws Exception;
    
}
