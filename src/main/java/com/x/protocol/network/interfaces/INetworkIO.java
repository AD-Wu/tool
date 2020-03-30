package com.x.protocol.network.interfaces;

/**
 * @Desc
 * @Date 2020-02-19 00:28
 * @Author AD
 */
public interface INetworkIO {
    
    INetworkService getService();
    
    INetworkConsent getConsent();
    
    <T extends INetworkInput> T getInput();
    
    <T extends INetworkOutput> T getOutput();
    
}
