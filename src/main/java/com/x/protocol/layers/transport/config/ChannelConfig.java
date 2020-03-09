package com.x.protocol.layers.transport.config;

import com.x.commons.util.string.Strings;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc
 * @Date 2020-03-09 21:21
 * @Author AD
 */
public class ChannelConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private String type;
    
    private String name;
    
    private boolean enabled;
    
    private int corePoolSize;
    
    private int maxPoolSize;
    
    private int queue;
    
    private long readTimeout;
    
    private long sendTimeout;
    
    private ProtocolConfig protocol;
    
    private ServerConfig server;
    
    private List<ClientConfig> clients;
    
    // ------------------------ 构造方法 ------------------------
    public ChannelConfig() {}
    // ------------------------ 方法定义 ------------------------
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public int getCorePoolSize() {
        return corePoolSize;
    }
    
    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
    
    public int getMaxPoolSize() {
        return maxPoolSize;
    }
    
    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }
    
    public int getQueue() {
        return queue;
    }
    
    public void setQueue(int queue) {
        this.queue = queue;
    }
    
    public long getReadTimeout() {
        return readTimeout;
    }
    
    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }
    
    public long getSendTimeout() {
        return sendTimeout;
    }
    
    public void setSendTimeout(long sendTimeout) {
        this.sendTimeout = sendTimeout;
    }
    
    public ProtocolConfig getProtocol() {
        return protocol;
    }
    
    public void setProtocol(ProtocolConfig protocol) {
        this.protocol = protocol;
    }
    
    public ServerConfig getServer() {
        return server;
    }
    
    public void setServer(ServerConfig server) {
        this.server = server;
    }
    
    public List<ClientConfig> getClients() {
        return clients;
    }
    
    public void setClients(List<ClientConfig> clients) {
        this.clients = clients;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
