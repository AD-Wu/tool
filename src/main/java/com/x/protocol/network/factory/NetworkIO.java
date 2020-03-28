package com.x.protocol.network.factory;

import com.x.protocol.network.interfaces.*;

/**
 * @Desc
 * @Date 2020-02-19 01:02
 * @Author AD
 */
public class NetworkIO implements INetworkIO {
    
    private NetworkService service;
    
    private NetworkConsent consent;
    
    private INetworkInput input;
    
    private INetworkOutput output;
    
    public NetworkIO(NetworkService service, NetworkConsent consent, INetworkInput input, INetworkOutput output) {
        this.service = service;
        this.consent = consent;
        this.input = input;
        this.output = output;
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
    public <T extends INetworkInput> T getInput() {
        return (T)this.input;
    }
    
    @Override
    public <T extends INetworkOutput> T getOutput() {
        return (T)this.output;
    }
    
}
