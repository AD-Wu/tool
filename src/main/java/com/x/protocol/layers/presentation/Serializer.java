package com.x.protocol.layers.presentation;

import com.x.commons.collection.NameValue;

import java.util.Collections;
import java.util.List;

/**
 * @Desc
 * @Date 2020-03-08 21:44
 * @Author AD
 */
public class Serializer {
    
    // ------------------------ 变量定义 ------------------------
    private final Class<?> serializerClass;
    
    private final List<NameValue> parameters;
    
    // ------------------------ 构造方法 ------------------------
    public Serializer(Class<?> serializerClass, List<NameValue> parameters) {
        this.serializerClass = serializerClass;
        this.parameters = Collections.unmodifiableList(parameters);
    }
    // ------------------------ 方法定义 ------------------------
    
    public Class<?> getSerializerClass() {
        return serializerClass;
    }
    
    public List<NameValue> getParameters() {
        return parameters;
    }
    
}
