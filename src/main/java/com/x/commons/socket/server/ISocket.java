package com.x.commons.socket.server;

/**
 * @Date 2019-10-18 21:21
 * @Author AD
 */
public interface ISocket {
    void start();
    void stop();
    void send(byte[] msg);
}
