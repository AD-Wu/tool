package com.x.protocol.core;

import com.x.commons.socket.config.ServerConfig;

import java.util.List;

/**
 * @Desc
 * @Date 2020-03-09 22:26
 * @Author AD
 */
public interface IStatusNotification {
    
    void onError(String protocolName, String name, String error);
    
    void onStart();
    
    void onStart(String protocolName);
    
    void onStop();
    
    void onStop(String protocolName);
    
    void onLoadConfig(List<ServerConfig> configs);
    
}
