package com.x.protocol.network.factory;

import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkInput;
import com.x.protocol.network.interfaces.INetworkService;

/**
 * @Desc TODO
 * @Date 2020-02-19 00:55
 * @Author AD
 */
public class NetworkInput implements INetworkInput {
    
    protected final NetworkService service;
    
    protected final NetworkConsent consent;
    
    private Object protocolReader;
    
    public NetworkInput(NetworkService service, NetworkConsent Consent) {
        this.service = service;
        this.consent = Consent;
    }
    
    @Override
    public INetworkService getService() {
        return this.service;
    }
    
    @Override
    public INetworkConsent getConsent() {
        return this.consent;
    }
    
    @Override
    public <T> T getProtocolReader() {
        return (T) this.protocolReader;
    }
    
    @Override
    public void setProtocolReader(Object protocolReader) {
        this.protocolReader = protocolReader;
    }
    
}
