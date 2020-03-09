package com.x.protocol.layers.transport.config;

import com.x.commons.collection.NameValue;
import com.x.commons.util.string.Strings;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc
 * @Date 2020-03-09 21:22
 * @Author AD
 */
public class ServerConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private int maxConnection;
    
    private List<NameValue> parameters;
    
    // ------------------------ 构造方法 ------------------------
    public ServerConfig() {}
    // ------------------------ 方法定义 ------------------------
    
    public int getMaxConnection() {
        return maxConnection;
    }
    
    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
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
