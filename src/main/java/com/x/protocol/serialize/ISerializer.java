package com.x.protocol.serialize;

import com.x.protocol.anno.info.DataInfo;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @Date 2019-01-05 22:04
 * @Author AD
 */
public interface ISerializer {

    Serializable serialize(final DataInfo data,final ByteBuffer buf) throws Exception;

}
