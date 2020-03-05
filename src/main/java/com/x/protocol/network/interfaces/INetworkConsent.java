package com.x.protocol.network.interfaces;

import com.x.protocol.network.core.NetworkConsentType;

/**
 * @Desc 网络应答对象
 * @Date 2020-02-19 00:13
 * @Author AD
 */
public interface INetworkConsent {
    
    String getName();
    
    boolean isAccepted();
    
    void setLoginMark(boolean login);
    
    boolean isLogin();
    
    String getConnectionInfo();
    
    long getConsentIndex();
    
    <T> T getInformation(String s);
    
    INetworkService getService();
    
    INetworkIO getNetworkIO();
    
    NetworkConsentType getType();
    
    String getLangKey();
    
    void setLangKey(String langKey);
    
    void close();
    
    boolean isClosed();
    
}
