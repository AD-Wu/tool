package com.x.protocol.core;

import com.x.commons.collection.DataSet;
import com.x.commons.events.Dispatcher;
import com.x.commons.events.Event;
import com.x.protocol.config.ServiceConfig;
import com.x.protocol.layers.transport.config.ClientConfig;
import org.slf4j.Logger;

import java.io.Serializable;

public interface IProtocol {
    
    boolean start(ServiceConfig service, IProtocolInitializer init, DataSet serviceParams, IStatusNotification status) throws Exception;
    
    void stop();
    
    int request(ChannelInfo[] infos, ChannelData data, IDataResponse resp, long index, ResponseMode mode);
    
    boolean requestTry(ChannelInfo[] infos, ChannelData data, IDataResponse resp, long index, ResponseMode mode);
    
    ResponseResult response(ChannelInfo info, ChannelData data, Serializable dataSerialized, DataSet dataSet, int status,
            String msg);
    
    ResponseResult responseTry(ChannelInfo info, ChannelData data, Serializable dataSerialized, DataSet dataSet, int status,
            String msg);
    
    String getName();
    
    boolean isDebug();
    
    void setDebug(boolean debug);
    
    boolean isStopped();
    
    ISessionManager getSessionManager();
    
    boolean addClient(String key, ClientConfig client);
    
    void removeClient(String key, String msg);
    
    ChannelInfo getClient(String key);
    
    ChannelInfo[] getClients();
    
    IChannel getChannel(String key);
    
    Dispatcher getDispatcher();
    
    int dispatch(Event event);
    
    IProtocolInitializer getInitializer();
    
    Logger getLogger();
    
    boolean hasFromRemoteControlKey(String key);
    
    boolean hasToRemoteControlKey(String key);
    
    String getActorCommand(Class<?> clazz);
    
    DataConfig getDataConfig(Class<?> clazz);
    
    void setTimeout(ChannelInfo info, ChannelData data, long timeout);
    
    void changeTimeout(ChannelInfo info, ChannelData data, int change);
    
    boolean cancelTimeout(ChannelInfo info, ChannelData data, boolean cancel);
    
}
