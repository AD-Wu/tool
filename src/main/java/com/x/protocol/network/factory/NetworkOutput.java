package com.x.protocol.network.factory;

import com.x.protocol.network.interfaces.INetworkOutput;

/**
 * @Desc 网络输出对象基类
 * @Date 2020-02-19 00:51
 * @Author AD
 */
public abstract class NetworkOutput implements INetworkOutput {
    
    /**
     * 网络服务
     */
    protected final NetworkService service;
    
    /**
     * 网络应答对象
     */
    protected final NetworkConsent consent;
    
    public NetworkOutput(NetworkService service, NetworkConsent consent) {
        this.service = service;
        this.consent = consent;
    }
    
    /**
     * 获取网络服务
     *
     * @return NetworkService 网络服务
     */
    public NetworkService getService() {
        return this.service;
    }
    
    /**
     * 获取网络应答对象
     *
     * @return NetworkConsent 网络应答对象
     */
    public NetworkConsent getConsent() {
        return this.consent;
    }
    
}
