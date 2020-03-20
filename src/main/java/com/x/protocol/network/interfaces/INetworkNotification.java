package com.x.protocol.network.interfaces;

/**
 * @Desc 网络通知器，即监听器
 * @Date 2020-02-19 00:35
 * @Author AD
 */
public interface INetworkNotification {
    
    boolean onConsentData(INetworkIO networkIO) throws Exception;
    
    boolean onConsentStart(INetworkConsent consent) throws Exception;
    
    void onConsentStop(INetworkConsent consent) throws Exception;
    
    void onError(INetworkService service, INetworkConsent consent, String errorMsg) throws Exception;
    
    void onMessage(INetworkService service, INetworkConsent consent, String msg) throws Exception;
    
    void onServiceStart(INetworkService service) throws Exception;
    
    void onServiceStop(INetworkService service) throws Exception;
    
}
