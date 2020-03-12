package com.x.commons.parser.bytes;

import com.x.commons.parser.core.IBytesParser;
import com.x.commons.util.convert.Converts;

/**
 * @Desc TODO
 * @Date 2020-03-13 01:01
 * @Author AD
 */
public class BooleanParser extends IBytesParser<Boolean> {
    
    @Override
    protected Boolean parseFrom(byte[] bytes) throws Exception {
        return Converts.toBoolean(bytes);
    }
    
}
