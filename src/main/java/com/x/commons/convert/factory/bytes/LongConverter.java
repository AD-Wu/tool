package com.x.commons.convert.factory.bytes;

import com.x.commons.convert.core.BytesConverter;
import com.x.commons.util.convert.Converts;

/**
 * @Desc TODO
 * @Date 2020-03-13 01:21
 * @Author AD
 */
public class LongConverter extends BytesConverter<Long> {
    
    @Override
    protected Long convertFrom(byte[] bytes) throws Exception {
        return Converts.toLong(bytes);
    }
    
    @Override
    protected byte[] reverseFrom(Long value) throws Exception {
        return Converts.toBytes(value);
    }
    
}
