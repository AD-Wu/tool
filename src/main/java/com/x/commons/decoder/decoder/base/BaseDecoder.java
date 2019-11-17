package com.x.commons.decoder.decoder.base;

import com.x.commons.decoder.core.IDecoder;
import com.x.protocol.anno.info.FieldInfo;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @Date 2019-01-05 22:07
 * @Author AD
 */
public abstract class BaseDecoder<T extends Serializable> implements IDecoder {

    @Override
    public final T decode(final FieldInfo fieldInfo, final ByteBuffer buf) throws Exception {
        byte[] bs = new byte[fieldInfo.getLength()];
        buf.get(bs);
        return decode(bs);
    }

    public abstract T decode(byte[] bs);

}
