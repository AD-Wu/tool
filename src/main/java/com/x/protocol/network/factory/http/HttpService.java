package com.x.protocol.network.factory.http;

import com.x.commons.collection.DataSet;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkNotification;

/**
 * @Desc TODO
 * @Date 2020-03-06 23:09
 * @Author AD
 */
public class HttpService extends NetworkService {
    
    public HttpService(INetworkNotification notification) {
        super(notification, true);
    }
    
    @Override
    protected Object getServiceInfo(String serviceProperty) {
        return null;
    }
    
    @Override
    protected void serviceStart() {
    
    }
    
    @Override
    protected void serviceStop() {
    
    }
    
    @Override
    public String getServiceInfo() {
        return null;
    }
    
    @Override
    public INetworkConsent connect(String name, DataSet data) throws Exception {
        return null;
    }
    
}
