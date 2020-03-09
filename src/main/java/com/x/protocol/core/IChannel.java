package com.x.protocol.core;

import com.x.protocol.layers.transport.config.ChannelConfig;
import com.x.protocol.layers.transport.config.ClientConfig;
import com.x.protocol.network.interfaces.INetworkService;

/**
 * @Desc
 * @Date 2020-03-08 22:59
 * @Author AD
 */
public interface IChannel {
    
    boolean start(ChannelConfig channel) throws Exception;
    
    void stop();
    
    boolean isStopped();
    
    boolean send(ChannelInfo info, ChannelData data);
    
    INetworkService getService();
    
    ISerializer getSerializer();
    
    void setSerializer(ISerializer serializer);
    
    String getName();
    
    boolean addClient(ClientConfig config);
    
    void removeClient(String key);
    
    int getRemoteCount();
    
    int getClientCount();
    
    long getSendTimeout();
    
    int getNextMappingIndex();
    
    void checkClientConnections(long index);
    
    boolean runSchedule(Runnable runnable);
    
}
