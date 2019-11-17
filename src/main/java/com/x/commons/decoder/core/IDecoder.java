package com.x.commons.decoder.core;

import com.x.protocol.anno.info.FieldInfo;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @Date 2018-12-22 19:45
 * @Author AD
 */
public interface IDecoder<T extends Serializable> {

    // Serializable encode(DataInfo dataInfo) throws Exception;

    T decode(FieldInfo fieldInfo,ByteBuffer buf) throws Exception;

}