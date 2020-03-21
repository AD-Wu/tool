package com.x.protocol.core;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-20 23:43
 * @Author AD
 */
public abstract class DataResponse<T extends Serializable> implements IDataResponse {
    
    // ------------------------ 变量定义 ------------------------
    protected IProtocol protocol;
    
    protected ChannelInfo channelInfo;
    
    protected ChannelData requestData;
    
    protected ChannelData responseData;
    
    // ------------------------ 构造方法 ------------------------
    public DataResponse() {}
    
    // ------------------------ 方法定义 ------------------------
    @Override
    public void onDataReady(IProtocol protocol, ChannelInfo info, ChannelData requestData) {
        this.protocol = protocol;
        this.channelInfo = info;
        this.requestData = requestData;
    }
    
    @Override
    public void onFailed(IProtocol protocol, ChannelInfo info, ChannelData data) {
        this.onResult(false, null);
    }
    
    @Override
    public void onResponse(IProtocol protocol, ChannelInfo info, ChannelData req, ChannelData resp) {
        if (resp != null && resp.isSucceed()) {
            this.onResult(true, resp.getDataBean());
        } else {
            this.onResult(false, null);
        }
    }
    
    public abstract void onResult(boolean a, T t);
    
}
