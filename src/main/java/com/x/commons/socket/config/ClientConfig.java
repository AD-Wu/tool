package com.x.commons.socket.config;

import com.x.protocol.anno.coreold.Doc;

/**
 * @Desc TODO
 * @Date 2019-11-01 23:17
 * @Author AD
 */
public class ClientConfig {

    // --------------------------- 成员变量 ---------------------------

    @Doc("服务器IP")
    private final String ip;

    @Doc("服务器端口")
    private final int port;

    @Doc("读超时时间")
    private int readTimeout;

    @Doc("写超时时间")
    private int writeTimeout;

    @Doc("空闲超时时间")
    private int idleTimeout;

    // --------------------------- 构造方法 ---------------------------

    public ClientConfig(String ip, int port) {
        this(new Builder(ip, port));
    }

    public ClientConfig(Builder builder) {
        this.ip = builder.ip;
        this.port = builder.port;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.idleTimeout = builder.idleTimeout;
    }

    // --------------------------- 成员方法 ---------------------------

    /**
     * 获取 读超时时间
     */
    public int getReadTimeout() {
        return this.readTimeout;
    }

    /**
     * 获取 写超时时间
     */
    public int getWriteTimeout() {
        return this.writeTimeout;
    }

    /**
     * 获取 空闲超时时间
     */
    public int getIdleTimeout() {
        return this.idleTimeout;
    }

    /**
     * 获取 服务器IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 获取 服务器端口
     */
    public int getPort() {
        return port;
    }

    // --------------------------- 构造器模式 ---------------------------

    public static class Builder {

        private final String ip;
        private final int port;
        private int readTimeout = 0;
        private int writeTimeout = 0;
        private int idleTimeout = 3;

        public Builder(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        public Builder ReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder WriteTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder idleTimeout(int idleTimeout) {
            this.idleTimeout = idleTimeout;
            return this;
        }

        public ClientConfig build() {
            return new ClientConfig(this);
        }

    }

}
