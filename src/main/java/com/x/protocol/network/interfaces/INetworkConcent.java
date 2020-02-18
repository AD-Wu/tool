package com.x.protocol.network.interfaces;

import com.x.protocol.network.core.NetworkConcentType;

/**
 * @Desc TODO
 * @Date 2020-02-19 00:13
 * @Author AD
 */
public interface INetworkConcent {
    
    String getName();
    
    boolean isAccepted();
    
    void setLoginMark(boolean login);
    
    boolean isLogin();
    
    String getConnectionInfo();
    
    long getConcentIndex();
    
    <T> T getInformation(String s);
    
    INetworkService getService();
    
    INetworkIO getNetworkIO();
    
    NetworkConcentType getType();
    
    String getLangKey();
    
    void setLangKey(String langKey);
    
    void close();
    
    boolean isClosed();
    
}
