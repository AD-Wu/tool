package com.x.commons.socket.client;

/**
 * @Desc TODO
 * @Date 2019-11-01 23:04
 * @Author AD
 */
public interface IClient {

    void start() throws Exception;
    void stop() throws Exception;
    void send(Object msg);
}
