package com.x.commons.socket.core;

import io.netty.buffer.ByteBuf;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc
 * @Date 2020-03-28 16:26
 * @Author AD
 */
public interface ISocketSerializer<T extends Serializable> {
    
    ByteBuf encode(T t,ByteBuf buf) throws Exception;
    
    List<T> decode(ByteBuf buf) throws Exception;
    
}
