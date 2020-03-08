package com.x.protocol.network.interfaces;

import com.x.commons.collection.DataSet;
import com.x.protocol.network.core.NetworkConfig;

/**
 * @Desc 网络服务
 * @Date 2020-02-19 00:04
 * @Author AD
 */
public interface INetworkService {
    
    NetworkConfig getConfig();
    
    String getName();
    
    String getType();
    
    boolean isServerMode();
    
    String getServiceInfo();
    
    boolean isStarted();
    
    boolean isStop();
    
    <T> T getInformation(String s);
    
    int getConsentSize();
    
    INetworkConsent[] getConsents();
    
    INetworkConsent getConsentByIndex(long index);
    
    boolean containConsent(long index);
    
    boolean runSchedule(Runnable runnable);
    
    INetworkConsent connect(String name, DataSet data) throws Exception;
    
    boolean start(NetworkConfig config);
    
    void stop();
    
}
