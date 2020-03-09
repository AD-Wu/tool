package com.x.protocol.layers.transport.config;

import com.x.commons.collection.NameValue;
import com.x.commons.util.string.Strings;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc
 * @Date 2020-03-09 21:21
 * @Author AD
 */
public class ClientConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private String name;
    
    private boolean enabled;
    
    private List<NameValue> parameters;
    
    // ------------------------ 构造方法 ------------------------
    public ClientConfig() {}
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
    
    public List<NameValue> getParameters() {
        return parameters;
    }
    
    public void setParameters(List<NameValue> parameters) {
        this.parameters = parameters;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
