package com.x.commons.collection;

import com.x.protocol.annotations.XField;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2019-12-08 13:09
 * @Author AD
 */
public class KeyValue implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @XField(doc = "数据键")
    private String k;
    @XField(doc = "数据值")
    private Object v;
    
    public KeyValue() {
    }
    
    public KeyValue(String key, Object value) {
        this.k = key;
        this.v = value;
    }
    
    public String getK() {
        return this.k;
    }
    
    public void setK(String key) {
        this.k = key;
    }
    
    public Object getV() {
        return this.v;
    }
    
    public void setV(Object value) {
        this.v = value;
    }
}
