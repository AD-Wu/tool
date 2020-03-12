package com.x.commons.convert.factory.bytes;

import com.x.commons.convert.core.BytesConverter;
import com.x.commons.util.convert.Converts;

/**
 * @Desc TODO
 * @Date 2020-03-13 01:22
 * @Author AD
 */
public class FloatConverter extends BytesConverter<Float> {
    
    @Override
    protected Float convertFrom(byte[] bytes) throws Exception {
        return Converts.toFloat(bytes);
    }
    
    @Override
    protected byte[] reverseFrom(Float value) throws Exception {
        return Converts.toBytes(value);
    }
    
}
