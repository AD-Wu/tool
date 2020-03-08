package com.x.protocol.layers.application.config;

import com.x.commons.util.string.Strings;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc
 * @Date 2020-03-08 20:00
 * @Author AD
 */
public class ApplicationConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private ActorsConfig actors;
    
    private List<ListenerConfig> listeners;
    
    private InitializerConfig initializer;
    
    private DatabaseConfig database;
    
    // ------------------------ 构造方法 ------------------------
    public ApplicationConfig() {}
    // ------------------------ 方法定义 ------------------------
    
    public ActorsConfig getActors() {
        return actors;
    }
    
    public void setActors(ActorsConfig actors) {
        this.actors = actors;
    }
    
    public List<ListenerConfig> getListeners() {
        return listeners;
    }
    
    public void setListeners(List<ListenerConfig> listeners) {
        this.listeners = listeners;
    }
    
    public InitializerConfig getInitializer() {
        return initializer;
    }
    
    public void setInitializer(InitializerConfig initializer) {
        this.initializer = initializer;
    }
    
    public DatabaseConfig getDatabase() {
        return database;
    }
    
    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
