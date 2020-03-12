package com.x.commons.convert.factory.bytes;

import com.x.commons.convert.core.BytesConverter;
import com.x.commons.util.convert.Converts;

/**
 * @Desc TODO
 * @Date 2020-03-13 01:21
 * @Author AD
 */
public class IntConverter extends BytesConverter<Integer> {
    
    @Override
    protected Integer convertFrom(byte[] bytes) throws Exception {
        return Converts.toInt(bytes);
    }
    
    @Override
    protected byte[] reverseFrom(Integer integer) throws Exception {
        return Converts.toBytes(integer);
    }
    
}
