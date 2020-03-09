package com.x.protocol.layers.transport.interfaces;

import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;

/**
 * @Desc
 * @Date 2020-03-09 21:29
 * @Author AD
 */
public interface ITransportResponse {
    
    void onField(ChannelInfo info, ChannelData data);
    
    void onResponse(ChannelInfo info, ChannelData send, ChannelData data);
    
}
