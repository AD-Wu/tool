package com.x.protocol.core;

/**
 * @Desc
 * @Date 2020-03-10 21:04
 * @Author AD
 */
public interface IDataResponse {
    
    void onDataReady(IProtocol protocol, ChannelInfo info, ChannelData req);
    
    void onFailed(IProtocol protocol, ChannelInfo info, ChannelData req);
    
    void onResponse(IProtocol protocol, ChannelInfo info, ChannelData req, ChannelData resp);
    
}
