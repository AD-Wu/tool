package com.x.commons.socket.server;

import com.x.commons.socket.bean.SocketConfig;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/27 16:54
 */
public class SocketServerConfig extends SocketConfig {

    // 是否保持连接存活，即心跳机制
    private final boolean keepalive;

    // 资源占满时允许保留的3次握手客户端数量，0表示默认50个
    private final int clientCount;

    public SocketServerConfig(int port) {
        super(port);
        this.keepalive = true;
        this.clientCount = 100;

    }

    public SocketServerConfig(int port, boolean keepalive, int clientCount) {
        super(port);
        this.keepalive = keepalive;
        this.clientCount = clientCount;
    }

    public boolean isKeepalive() {
        return keepalive;
    }


    public int getClientCount() {
        return clientCount;
    }

}
