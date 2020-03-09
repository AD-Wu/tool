package com.x.protocol.layers.transport;

import com.x.commons.util.string.Strings;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.ResponseMode;
import com.x.protocol.layers.transport.interfaces.ITransportResponse;
import com.x.protocol.network.interfaces.INetworkConsent;

/**
 * @Desc
 * @Date 2020-03-09 21:21
 * @Author AD
 */
public final class CallbackInfo {
    
    // ------------------------ 变量定义 ------------------------
    private final String key;
    
    private final ChannelInfo channelInfo;
    
    private final INetworkConsent consent;
    
    private final ITransportResponse response;
    
    private final ChannelData data;
    
    private long timeout;
    
    private final ResponseMode responseMode;
    
    private long startTime;
    
    // ------------------------ 构造方法 ------------------------
    
    CallbackInfo(String key, ChannelInfo channelInfo, ChannelData data,
            ITransportResponse response, long timeout, ResponseMode responseMode) {
        this.key = key;
        this.channelInfo = channelInfo;
        this.consent = channelInfo.getConsent();
        this.data = data;
        this.response = response;
        this.timeout = timeout;
        this.responseMode = responseMode;
        this.startTime = System.currentTimeMillis();
    }
    
    // ------------------------ 方法定义 ------------------------
    boolean checkTimeout(long now) {
        return now - startTime >= timeout ||
               now < startTime - 60000L ||
               consent.isClosed();
    }
    
    void resetTimeout(long timeout) {
        this.startTime = System.currentTimeMillis();
        this.timeout = timeout;
    }
    
    void changeTimeout(int value) {
        this.timeout += value;
    }
    
    // ---------------------- get and set ----------------------
    
    public String getKey() {
        return key;
    }
    
    public ChannelInfo getChannelInfo() {
        return channelInfo;
    }
    
    public INetworkConsent getConsent() {
        return consent;
    }
    
    public ITransportResponse getResponse() {
        return response;
    }
    
    public ChannelData getData() {
        return data;
    }
    
    public long getTimeout() {
        return timeout;
    }
    
    public ResponseMode getResponseMode() {
        return responseMode;
    }
    
    public long getStartTime() {
        return startTime;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
