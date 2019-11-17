package com.x.commons.socket.config;

import com.x.protocol.anno.core.Doc;
import lombok.ToString;

/**
 * Socket config
 *
 * @Date 2019-10-18 22:54
 * @Author AD
 */
@ToString
public class ServerConfig {

    // ------------------------- 成员变量 -------------------------

    @Doc("端口")
    private final int port;

    @Doc("是否保持连接存活，即心跳机制")
    private final boolean keepalive;

    @Doc("资源占满时允许保留的3次握手客户端数量，0表示默认50个")
    private final int clientCount;

    @Doc("多长时间没有读取数据则断开连接，默认0:不开启，单位：分钟")
    private final int readTimeout;

    @Doc("多长时间没有写数据则断开连接，默认0:不开启，单位：分钟")
    private final int writeTimeout;

    @Doc("连接空闲时间，默认3分钟")
    private final int idleTimeout;

    // ------------------------- 构造方法 -------------------------

    public ServerConfig(int port) {
        this(new Builder(port));
    }

    public ServerConfig(Builder builder) {
        this.port = builder.port;
        this.keepalive = builder.keepalive;
        this.clientCount = builder.clientCount;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.idleTimeout = builder.idleTimeout;
    }

    // ------------------------- 成员方法 -------------------------

    public int getPort() {
        return port;
    }

    public boolean isKeepalive() {
        return keepalive;
    }

    public int getClientCount() {
        return clientCount;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    // ------------------------- 构造器模式 -------------------------

    public static class Builder {

        private final int port;

        private boolean keepalive = true;

        private int clientCount = 0;// 0:默认50

        private int readTimeout = 0;

        private int writeTimeout = 0;

        private int idleTimeout = 3;

        public Builder(int port) {
            this.port = port;
        }

        public Builder keepalive(boolean keepalive) {
            this.keepalive = keepalive;
            return this;
        }

        public Builder clientCount(int clientCount) {
            this.clientCount = clientCount;
            return this;
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

        public ServerConfig build() {
            return new ServerConfig(this);
        }

    }

}
