package com.x.protocol.network.interfaces;

/**
 * @Desc TODO
 * @Date 2020-02-19 00:31
 * @Author AD
 */
public interface INetworkInput {
    
    INetworkService getService();
    
    INetworkConcent getConcent();
    
    <T> T getProtocolReader();
    
    void setProtocolReader(Object protocolReader);
    
}
