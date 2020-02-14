package com.x.commons.util.http.data;

import java.io.File;

/**
 * @Desc TODO
 * @Date 2020-01-29 20:33
 * @Author AD
 */
public final class ValueType<T> {
    
    private final ValueType valueType;
    
    private final Object key;
    
    ValueType(ValueType valueType, Object key) {
        this.valueType = valueType;
        this.key = key;
    }
    
    public static final ValueType<Object> OBJECT = new ValueType<>(null, null);
    
    public static final ValueType<String> FILE_PATH = new ValueType<>(null, null);
    
    public static final ValueType<File> FILE = new ValueType<>(null, null);
    
    public static final ValueType<byte[]> BYTES = new ValueType<>(null, null);
    
    public Object getKey() {
        return this.key;
    }
    
    public ValueType get() {
        return this.valueType;
    }
    
}
