package com.x.protocol.core;

/**
 * @Desc
 * @Date 2020-03-10 21:04
 * @Author AD
 */
public interface IDataResponse {
    
    void onDataResponse(IProtocol prtc, ChannelInfo info, ChannelData data);
    
    void onFailed(IProtocol prtc, ChannelInfo info, ChannelData data);
    
    void onResponse(IProtocol prtc, ChannelInfo info, ChannelData send, ChannelData recv);
    
}
