package com.x.commons.socket.core;

import com.x.commons.util.string.Strings;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/27 16:38
 */
public class SocketConfig {

    // 端口
    protected final int port;

    // 多长时间没有读取数据则断开连接，默认0:不开启，单位：分钟
    protected int readTimeout = 0;

    // 多长时间没有写数据则断开连接，默认0:不开启，单位：分钟
    protected int writeTimeout = 0;

    // 连接空闲时间，默认3分钟
    protected int idleTimeout = 3;

    public SocketConfig(int port){
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
}
