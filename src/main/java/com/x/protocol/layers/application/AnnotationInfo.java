package com.x.protocol.layers.application;

import com.x.commons.util.string.Strings;
import com.x.protocol.core.DataConfig;

import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-08 19:58
 * @Author AD
 */
public class AnnotationInfo {
    
    // ------------------------ 变量定义 ------------------------
    private final List<Class<?>> actors;
    
    private final Map<Class<?>, DataConfig> dataConfigs;
    
    private final List<Class<?>> readyActors;
    
    private final List<Class<?>> interfaces;
    
    // ------------------------ 构造方法 ------------------------
    public AnnotationInfo(
            List<Class<?>> actors,
            Map<Class<?>, DataConfig> dataConfigs,
            List<Class<?>> readyActors,
            List<Class<?>> interfaces) {
        this.actors = actors;
        this.dataConfigs = dataConfigs;
        this.readyActors = readyActors;
        this.interfaces = interfaces;
    }
    
    // ------------------------ 方法定义 ------------------------
    
    public List<Class<?>> getActors() {
        return actors;
    }
    
    public Map<Class<?>, DataConfig> getDataConfigs() {
        return dataConfigs;
    }
    
    public List<Class<?>> getReadyActors() {
        return readyActors;
    }
    
    public List<Class<?>> getInterfaces() {
        return interfaces;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
