package com.x.commons.convert.factory.bytes;

import com.x.commons.convert.core.BytesConverter;
import com.x.commons.util.convert.Converts;

/**
 * @Desc TODO
 * @Date 2020-03-13 01:09
 * @Author AD
 */
public class ByteConverter extends BytesConverter<Byte> {
    
    @Override
    protected Byte convertFrom(byte[] bytes) throws Exception {
        return Converts.toByte(bytes);
    }
    
    @Override
    protected byte[] reverseFrom(Byte b) throws Exception {
        return Converts.toBytes(b);
    }
    
}
