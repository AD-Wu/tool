package com.x.commons.socket.bean;

import com.x.commons.util.string.Strings;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/27 16:38
 */
public class SocketConfig {
    // ------------------------ 变量定义 ------------------------
    
    // ------------------------ 公共变量 ------------------------
    private boolean serverMode = false;
    
    private int port;
    
    // 多长时间没有读取数据则断开连接，默认0:不开启，单位：分钟
    private int readTimeout = 0;
    
    // 多长时间没有写数据则断开连接，默认0:不开启，单位：分钟
    private int writeTimeout = 0;
    
    // 连接空闲时间，默认3分钟
    private int idleTimeout = 3;
    
    // ------------------------ 服务端变量 ------------------------
    
    // 是否保持连接存活，即心跳机制
    private boolean keepalive = true;
    
    // 资源占满时允许保留的3次握手客户端数量，0表示默认50个
    private int clientCount = 50;
    
    // ------------------------ 客户端变量 ------------------------
    private String ip;
    
    // ------------------------ 构造方法 ------------------------
    public static SocketConfig localClient(int port) {
        SocketConfig config = new SocketConfig();
        config.setIp("localhost");
        config.setPort(port);
        return config;
    }
    
    public static SocketConfig client(String ip, int port) {
        SocketConfig config = new SocketConfig();
        config.setIp(ip);
        config.setPort(port);
        return config;
    }
    
    public static SocketConfig server(int port) {
        SocketConfig config = new SocketConfig();
        config.setPort(port);
        config.serverMode = true;
        return config;
    }
    // ------------------------ 方法定义 ------------------------
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
    
    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }
    
    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }
    
    public void setKeepalive(boolean keepalive) {
        this.keepalive = keepalive;
    }
    
    public void setClientCount(int clientCount) {
        this.clientCount = clientCount;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public boolean isServerMode() {
        return serverMode;
    }
    
    public int getPort() {
        return port;
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
    
    public boolean isKeepalive() {
        return keepalive;
    }
    
    public int getClientCount() {
        return clientCount;
    }
    
    public String getIp() {
        return ip;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
