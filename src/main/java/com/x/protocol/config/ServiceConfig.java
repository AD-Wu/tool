package com.x.protocol.config;

import com.x.commons.util.string.Strings;
import com.x.protocol.layers.application.config.ApplicationConfig;
import com.x.protocol.layers.presentation.config.SerializerConfig;
import com.x.protocol.layers.session.config.SessionConfig;
import com.x.protocol.layers.transport.config.ChannelConfig;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc
 * @Date 2020-03-10 20:58
 * @Author AD
 */
public class ServiceConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private String name;
    
    private boolean enabled;
    
    private boolean debug;
    
    private String remark;
    
    private ApplicationConfig application;
    
    private List<SerializerConfig> presentations;
    
    private SessionConfig session;
    
    private List<ChannelConfig> transports;
    
    // ------------------------ 构造方法 ------------------------
    public ServiceConfig() {}
    // ------------------------ 方法定义 ------------------------
    
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
    
    public boolean isDebug() {
        return debug;
    }
    
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public ApplicationConfig getApplication() {
        return application;
    }
    
    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }
    
    public List<SerializerConfig> getPresentations() {
        return presentations;
    }
    
    public void setPresentations(List<SerializerConfig> presentations) {
        this.presentations = presentations;
    }
    
    public SessionConfig getSession() {
        return session;
    }
    
    public void setSession(SessionConfig session) {
        this.session = session;
    }
    
    public List<ChannelConfig> getTransports() {
        return transports;
    }
    
    public void setTransports(List<ChannelConfig> transports) {
        this.transports = transports;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
