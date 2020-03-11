package com.x.commons.serialize.core;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-12 01:08
 * @Author AD
 */
public interface ISerializer<T extends Serializable> {
    
    Serializable serialize(Class<T> target) throws Exception;
    
    Serializable deserialize(byte[] data, Class<T> target) throws Exception;
    
}
