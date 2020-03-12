package com.x.commons.parser.bytes;

import com.x.commons.parser.core.IBytesParser;
import com.x.commons.util.convert.Converts;

/**
 * @Desc
 * @Date 2020-03-13 00:50
 * @Author AD
 */
public class ShortParser extends IBytesParser<Short> {
    
    @Override
    protected Short parseFrom(byte[] bytes) throws Exception {
        return Converts.toShort(bytes);
    }
    
}
