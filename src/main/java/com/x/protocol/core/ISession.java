package com.x.protocol.core;

import java.time.LocalDateTime;

/**
 * @Desc
 * @Date 2020-03-08 22:44
 * @Author AD
 */
public interface ISession {
    
    String getID();
    
    ISerializer getSerializer();
    
    Object setData(String key, Object value);
    
    <T> T getData(String key);
    
    <T> T removeData(String key);
    
    boolean containDataKey(String key);
    
    boolean containData(Object data);
    
    boolean containChannelInfo(ChannelInfo info);
    
    int getChannelInfoSize();
    
    ChannelInfo[] getChannelInfos();
    
    LocalDateTime getCreateTime();
    
    void updateSessionTime();
    
    String getLangKey();
    
    void setLangKey(String langKey);
    
}
