package com.x.protocol.core;

import com.x.commons.socket.config.ServerConfig;

import java.util.List;

/**
 * @Desc
 * @Date 2020-03-09 22:26
 * @Author AD
 */
public interface IStatusNotification {
    
    void onError(String s, String ss, String sss);
    
    void onStart();
    
    void onStart(String msg);
    
    void onStop();
    
    void onStop(String msg);
    
    void onLoadConfig(List<ServerConfig> configs);
    
}
