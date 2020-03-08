package com.x.protocol.core;

import java.io.Serializable;

/**
 * @Desc TODO
 * @Date 2020-03-08 21:49
 * @Author AD
 */
public interface ISerializer {
    
    void add(String key, Object value);
    
    Serializable serialize(DataInfo dataInfo, Serializable data) throws Exception;
    
    Serializable deserialize(DataInfo dataInfo, Serializable data) throws Exception;
    
}
