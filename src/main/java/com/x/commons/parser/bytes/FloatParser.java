package com.x.commons.parser.bytes;

import com.x.commons.parser.core.IBytesParser;
import com.x.commons.util.convert.Converts;

/**
 * @Desc TODO
 * @Date 2020-03-13 00:59
 * @Author AD
 */
public class FloatParser extends IBytesParser<Float> {
    
    @Override
    protected Float parseFrom(byte[] bytes) throws Exception {
        return Converts.toFloat(bytes);
    }
    
}
