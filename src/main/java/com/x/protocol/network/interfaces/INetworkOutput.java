package com.x.protocol.network.interfaces;

/**
 * @Desc TODO
 * @Date 2020-02-19 00:30
 * @Author AD
 */
public interface INetworkOutput {
    INetworkService getService();
    INetworkConcent getConcent();
    void flush();
}
