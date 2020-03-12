package com.x.commons.convert.factory.bytes;

import com.x.commons.convert.core.BytesConverter;
import com.x.commons.util.convert.Converts;

/**
 * @Desc TODO
 * @Date 2020-03-13 01:24
 * @Author AD
 */
public class BooleanConverter extends BytesConverter<Boolean> {
    
    @Override
    protected Boolean convertFrom(byte[] bytes) throws Exception {
        return Converts.toBoolean(bytes);
    }
    
    @Override
    protected byte[] reverseFrom(Boolean bool) throws Exception {
        return Converts.toBytes(bool ? 1 : 0);
    }
    
}
