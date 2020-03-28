package com.x.commons.socket.client;

import com.x.commons.socket.core.SocketConfig;
import com.x.commons.util.string.Strings;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/27 17:12
 */
public class SocketClientConfig extends SocketConfig {

    private final String ip;

    public SocketClientConfig(String ip,int port) {
        super(port);
        this.ip = ip;
    }
    
    public static SocketClientConfig getLocal(int port){
        return new SocketClientConfig("localhost", port);
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
}
