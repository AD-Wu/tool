package com.x.commons.convert.factory.bytes;

import com.x.commons.convert.core.BytesConverter;
import com.x.commons.util.convert.Converts;

/**
 * @Desc TODO
 * @Date 2020-03-13 01:23
 * @Author AD
 */
public class DoubleConverter extends BytesConverter<Double> {
    
    @Override
    protected Double convertFrom(byte[] bytes) throws Exception {
        return Converts.toDouble(bytes);
    }
    
    @Override
    protected byte[] reverseFrom(Double value) throws Exception {
        return Converts.toBytes(value);
    }
    
}
