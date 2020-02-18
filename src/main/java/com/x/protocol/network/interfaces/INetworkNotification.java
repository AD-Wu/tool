package com.x.protocol.network.interfaces;

/**
 * @Desc TODO
 * @Date 2020-02-19 00:35
 * @Author AD
 */
public interface INetworkNotification {
    
    boolean onConcentData(INetworkIO networkIO) throws Exception;
    
    boolean onConcentStart(INetworkConcent concent) throws Exception;
    
    void onConcentStop(INetworkConcent concent) throws Exception;
    
    void onError(INetworkService service, INetworkConcent concent, String errorMsg) throws Exception;
    
    void onMessage(INetworkService service, INetworkConcent concent, String msg) throws Exception;
    
    void onServiceStart(INetworkService service) throws Exception;
    
    void onServiceStop(INetworkService service) throws Exception;
    
}
