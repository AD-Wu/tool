package com.x.protocol.network.interfaces;

import com.x.commons.collection.DataSet;
import com.x.protocol.network.core.NetworkConfig;

/**
 * @Desc TODO
 * @Date 2020-02-19 00:04
 * @Author AD
 */
public interface INetworkService {
    
    NetworkConfig getConfig();
    
    String getName();
    
    String getType();
    
    boolean isServerMode();
    
    String getServiceInfo();
    
    boolean isStarted();
    
    boolean isStop();
    
    <T> T getInformation(String s);
    
    int getConcentSize();
    
    INetworkConcent[] getConcents();
    
    INetworkConcent getConcentByIndex(long index);
    
    INetworkConcent connect(String address, DataSet data) throws Exception;
    
    boolean containConcent(long index);
    
    boolean runSchedule(Runnable runnable);
    
}
