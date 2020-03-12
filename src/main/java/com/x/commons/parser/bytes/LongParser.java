package com.x.commons.parser.bytes;

import com.x.commons.parser.core.IBytesParser;
import com.x.commons.util.convert.Converts;

/**
 * @Desc TODO
 * @Date 2020-03-13 00:58
 * @Author AD
 */
public class LongParser extends IBytesParser<Long> {
    
    @Override
    protected Long parseFrom(byte[] bytes) throws Exception {
        return Converts.toLong(bytes);
    }
    
}
