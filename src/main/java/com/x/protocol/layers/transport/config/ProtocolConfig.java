package com.x.protocol.layers.transport.config;

import com.x.commons.util.string.Strings;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-09 21:21
 * @Author AD
 */
public class ProtocolConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private String readerClass;
    
    private String sendClass;
    
    private int maxMappingIndex;
    
    // ------------------------ 构造方法 ------------------------
    public ProtocolConfig() {}
    // ------------------------ 方法定义 ------------------------
    
    public String getReaderClass() {
        return readerClass;
    }
    
    public void setReaderClass(String readerClass) {
        this.readerClass = readerClass;
    }
    
    public String getSendClass() {
        return sendClass;
    }
    
    public void setSendClass(String sendClass) {
        this.sendClass = sendClass;
    }
    
    public int getMaxMappingIndex() {
        return maxMappingIndex;
    }
    
    public void setMaxMappingIndex(int maxMappingIndex) {
        this.maxMappingIndex = maxMappingIndex;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
