package com.x.protocol.layers.presentation;

import com.x.protocol.core.DataInfo;
import com.x.protocol.core.ISerializer;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-08 21:56
 * @Author AD
 */
public class DefaultSerializer implements ISerializer {
    
    @Override
    public void add(String key, Object value) {
    
    }
    
    @Override
    public Serializable serialize(DataInfo dataInfo, Serializable data) throws Exception {
        return data;
    }
    
    @Override
    public Serializable deserialize(DataInfo dataInfo, Serializable data) throws Exception {
        return data;
    }
    
}
