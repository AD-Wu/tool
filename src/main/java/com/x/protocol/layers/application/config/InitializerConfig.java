package com.x.protocol.layers.application.config;

import com.x.commons.collection.NameValue;
import com.x.commons.util.string.Strings;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc
 * @Date 2020-03-08 19:59
 * @Author AD
 */
public class InitializerConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private String clazz;
    
    private List<NameValue> parameters;
    
    // ------------------------ 构造方法 ------------------------
    public InitializerConfig() {}
    // ------------------------ 方法定义 ------------------------
    
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
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
