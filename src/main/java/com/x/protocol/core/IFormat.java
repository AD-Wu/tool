package com.x.protocol.core;

import java.io.Serializable;
import java.nio.ByteBuffer;

public interface IFormat<T extends Serializable> {

    T toData(ByteBuffer buf) throws Exception;

    byte[] toBytes(T t) throws Exception;
}
