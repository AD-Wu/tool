package com.x.protocol.layers.transport.interfaces;

import com.x.commons.collection.DataSet;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IChannel;
import com.x.protocol.core.IStatusNotification;
import com.x.protocol.network.core.NetworkConfig;

/**
 * @Desc
 * @Date 2020-03-09 22:26
 * @Author AD
 */
public interface ITransportNotification extends IStatusNotification {
    
    boolean onChannelPrepare(IChannel channel, NetworkConfig config);
    
    boolean onClientPrepare(IChannel channel, DataSet dataSet);
    
    void onChannelStart(IChannel channel);
    
    void onChannelStop(IChannel channel);
    
    void onServiceStart();
    
    void onServiceStop();
    
    boolean onConnected(ChannelInfo info) throws Exception;
    
    void onDataRequest(ChannelInfo info, ChannelData data) throws Exception;
    
    boolean notifyClientConnected(ChannelInfo info);
    
    void notifyClientDisconnected(ChannelInfo info);
    
    boolean notifyRemoteConnected(ChannelInfo info);
    
    void notifyRemoteDisconnected(ChannelInfo info);
    
}
