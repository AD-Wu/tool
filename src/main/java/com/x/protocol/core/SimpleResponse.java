package com.x.protocol.core;

/**
 * @Desc
 * @Date 2020-03-20 23:53
 * @Author AD
 */
public abstract class SimpleResponse implements IDataResponse {
    
    @Override
    public void onDataReady(IProtocol protocol, ChannelInfo info, ChannelData req) {
    
    }
    
    @Override
    public void onFailed(IProtocol protocol, ChannelInfo info, ChannelData req) {
        this.onResult(false, info, req, null);
    }
    
    @Override
    public void onResponse(IProtocol protocol, ChannelInfo info, ChannelData req, ChannelData resp) {
        if (resp != null && resp.isSucceed()) {
            this.onResult(true, info, req, resp);
        } else {
            this.onResult(false, info, req, resp);
        }
    }
    
    public abstract void onResult(boolean succeed, ChannelInfo info, ChannelData req, ChannelData resp);
    
}
