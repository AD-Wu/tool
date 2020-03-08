package com.x.protocol.core;

import com.x.commons.collection.KeyValue;

/**
 * @Desc
 * @Date 2020-03-08 23:24
 * @Author AD
 */
public interface ISessionManager {
    
    ISession createSession(ChannelInfo info, KeyValue... kvs);
    
    ISession createSession(String key, KeyValue... kvs);
    
    ISession createSession(String key, ChannelInfo info, KeyValue... kvs);
    
    ISession createSessionWithSerializer(ChannelInfo info, KeyValue... kvs);
    
    ISession createSessionWithSerializer(String key, ChannelInfo info, KeyValue... kvs);
    
    boolean isLogin(String key);
    
    boolean isLoginCommand(String key, String s);
    
    ISession getSession(ChannelInfo info);
    
    ISession getSession(String key);
    
    int getSessionSize();
    
    ISession[] getSessions();
    
    boolean ChannelLogin(ISession session, ChannelInfo info);
    
    boolean isChannelLogin(ChannelInfo info);
    
    void ChannelLogout(ChannelInfo info, boolean a, boolean b);
    
    void endSession(String key);
    
    void endSession(String key, String s);
    
}
