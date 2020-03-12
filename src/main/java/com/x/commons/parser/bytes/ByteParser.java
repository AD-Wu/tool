package com.x.commons.parser.bytes;

import com.x.commons.parser.core.IBytesParser;
import com.x.commons.util.convert.Converts;

/**
 * @Desc TODO
 * @Date 2020-03-13 00:52
 * @Author AD
 */
public class ByteParser extends IBytesParser<Byte> {
    
    @Override
    protected Byte parseFrom(byte[] bytes) throws Exception {
        return Converts.toByte(bytes);
    }
    
}
