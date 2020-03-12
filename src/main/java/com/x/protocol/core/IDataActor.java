package com.x.protocol.core;

/**
 * @Desc
 * @Date 2020-03-12 22:29
 * @Author AD
 */
public interface IDataActor {
    
    void onDataRequest(IProtocol prtc, ChannelInfo info, ChannelData data) throws Exception;
    
}
