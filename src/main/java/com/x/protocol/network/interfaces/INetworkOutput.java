package com.x.protocol.network.interfaces;

/**
 * @Desc 网络输出
 * @Date 2020-02-19 00:30
 * @Author AD
 */
public interface INetworkOutput {
    
    INetworkService getService();
    
    INetworkConsent getConsent();
    
    void flush();
    
}
