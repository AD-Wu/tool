package com.x.protocol.layers.application;

import com.x.commons.util.bean.New;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.DataConfig;
import com.x.protocol.core.DataInfo;
import com.x.protocol.core.IProtocol;
import com.x.protocol.layers.application.config.ActorsConfig;
import com.x.protocol.layers.application.config.ApplicationConfig;

import java.util.Map;
import java.util.Set;

/**
 * @Desc TODO
 * @Date 2020-03-08 19:57
 * @Author AD
 */
public class Application {
    
    // ------------------------ 变量定义 ------------------------
    private Map<String, DataInfo> fromRemoteRequest = New.concurrentMap();
    
    private Map<String, DataInfo> fromRemoteResponse = New.concurrentMap();
    
    private Set<String> fromRemoteCmdNoCtrl = New.concurrentSet();
    
    private Map<String, DataInfo> toRemoteRequest = New.concurrentMap();
    
    private Map<String, DataInfo> toRemoteResponse = New.concurrentMap();
    
    private Set<String> toRemoteCmdNoCtrl = New.concurrentSet();
    
    private Map<Class<?>, DataConfig> dataConfigs = New.concurrentMap();
    
    private Map<Class<?>, String> actorCmdMap = New.concurrentMap();
    
    // ------------------------ 构造方法 ------------------------
    public Application(IProtocol protocol, ApplicationConfig appConfig) throws Exception {
        ActorsConfig actorsConf = appConfig.getActors();
        if(actorsConf!=null){
        
        }
    }
    // ------------------------ 方法定义 ------------------------
    
    public boolean hasFromRemoteControlKey(String key) {
        return false;
    }

    public boolean hasToRemoteControlKey(String key) {
        return false;
    }
    
    public DataConfig getDataConfig(Class<?> clazz) {
        return null;
    }
    
    public String getActorCommand(Class<?> clazz) {
        return null;
    }

    public DataInfo getToRemoteResponseConfig(ChannelData data) {
        return null;
   }
    public DataInfo getToRemoteRequestConfig(ChannelData reqData) {
        return null;
    }
    public DataInfo getFromRemoteRequestConfig(ChannelData data) {
        return null;
    }
    public DataInfo getFromRemoteResponseConfig(ChannelData data) {
        return null;
    }
    // ------------------------ 私有方法 ------------------------
    
}
