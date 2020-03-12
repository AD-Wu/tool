package com.x.commons.convert.factory.bytes;

import com.x.commons.convert.core.BytesConverter;
import com.x.commons.util.convert.Converts;

/**
 * @Desc TODO
 * @Date 2020-03-13 01:19
 * @Author AD
 */
public class ShortConverter extends BytesConverter<Short> {
    
    @Override
    protected Short convertFrom(byte[] bytes) throws Exception {
        return Converts.toShort(bytes);
    }
    
    @Override
    protected byte[] reverseFrom(Short value) throws Exception {
        return Converts.toBytes(value);
    }
    
}
