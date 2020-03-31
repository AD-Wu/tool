package com.x.commons.socket.bean;

import java.util.StringJoiner;

/**
 * @Desc
 * @Date 2020-03-31 23:40
 * @Author AD
 */
public class SocketInfo {
    
    private String remoteAddress;
    
    private String remoteHost;
    
    private int remotePort;
    
    private String localAddress;
    
    private String localHost;
    
    private int localPort;
    
    public SocketInfo(String remoteAddress, String localAddress) {
        this.remoteAddress = remoteAddress;
        this.localAddress = localAddress;
    }
    
    public String getRemoteAddress() {
        return remoteAddress;
    }
    
    public String getRemoteHost() {
        return remoteHost;
    }
    
    public int getRemotePort() {
        return remotePort;
    }
    
    public String getLocalAddress() {
        return localAddress;
    }
    
    public String getLocalHost() {
        return localHost;
    }
    
    public int getLocalPort() {
        return localPort;
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", SocketInfo.class.getSimpleName() + "[", "]")
                .add("remoteAddress='" + remoteAddress + "'")
                .add("localAddress='" + localAddress + "'")
                .toString();
    }
    
}
