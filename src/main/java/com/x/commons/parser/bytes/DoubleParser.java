package com.x.commons.parser.bytes;

import com.x.commons.parser.core.IBytesParser;
import com.x.commons.util.convert.Converts;

/**
 * @Desc TODO
 * @Date 2020-03-13 01:00
 * @Author AD
 */
public class DoubleParser extends IBytesParser<Double> {
    
    @Override
    protected Double parseFrom(byte[] bytes) throws Exception {
        return Converts.toDouble(bytes);
    }
    
}
