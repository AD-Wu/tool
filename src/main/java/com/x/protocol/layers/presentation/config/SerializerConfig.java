package com.x.protocol.layers.presentation.config;

import com.x.commons.collection.NameValue;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc
 * @Date 2020-03-08 21:47
 * @Author AD
 */
public class SerializerConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private String channel;
    
    private String clazz;
    
    private List<NameValue> parameters;
    
    // ------------------------ 构造方法 ------------------------
    public SerializerConfig() {}
    // ------------------------ 方法定义 ------------------------
    
    public String getChannel() {
        return channel;
    }
    
    public void setChannel(String channel) {
        this.channel = channel;
    }
    
    public String getClazz() {
        return clazz;
    }
    
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
    
    public List<NameValue> getParameters() {
        return parameters;
    }
    
    public void setParameters(List<NameValue> parameters) {
        this.parameters = parameters;
    }
    
}
