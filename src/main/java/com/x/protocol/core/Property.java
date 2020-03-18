package com.x.protocol.core;

import java.lang.reflect.Field;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/12 17:38
 */
public class Property {
    // ------------------------ 变量定义 ------------------------
    
    private String id;
    
    private String sid;
    
    private int min;
    
    private int max;
    
    private int length;
    
    private String lengthProp;
    
    private boolean pk;
    
    private String doc;
    
    private Field field;
    
    private Class<?> type;
    
    private Class<?> dataClass;
    
    private DataConfig dataConfig;
    
    // ------------------------ 构造方法 ------------------------
    public Property() {}
    // ------------------------ 方法定义 ------------------------
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getSid() {
        return sid;
    }
    
    public void setSid(String sid) {
        this.sid = sid;
    }
    
    public int getMin() {
        return min;
    }
    
    public void setMin(int min) {
        this.min = min;
    }
    
    public int getMax() {
        return max;
    }
    
    public void setMax(int max) {
        this.max = max;
    }
    
    public int getLength() {
        return length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public String getLengthProp() {
        return lengthProp;
    }
    
    public void setLengthProp(String lengthProp) {
        this.lengthProp = lengthProp;
    }
    
    public boolean isPk() {
        return pk;
    }
    
    public void setPk(boolean pk) {
        this.pk = pk;
    }
    
    public String getDoc() {
        return doc;
    }
    
    public void setDoc(String doc) {
        this.doc = doc;
    }
    
    public Field getField() {
        return field;
    }
    
    public void setField(Field field) {
        this.field = field;
    }
    
    public Class<?> getType() {
        return type;
    }
    
    public void setType(Class<?> type) {
        this.type = type;
    }
    
    public Class<?> getDataClass() {
        return dataClass;
    }
    
    public void setDataClass(Class<?> dataClass) {
        this.dataClass = dataClass;
    }
    
    public DataConfig getDataConfig() {
        return dataConfig;
    }
    
    public void setDataConfig(DataConfig dataConfig) {
        this.dataConfig = dataConfig;
    }
    
}
