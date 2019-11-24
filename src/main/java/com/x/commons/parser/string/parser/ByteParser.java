package com.x.commons.parser.string.parser;

import com.x.commons.util.string.Strings;
import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.parser.string.core.IStringParser;

/**
 * @Desc TODO
 * @Date 2019-11-21 22:43
 * @Author AD
 */
@Parser(result = {byte.class, Byte.class})
public class ByteParser extends IStringParser<Byte, String> {
    
    @Override
    public Byte parseFrom(String s) throws Exception {
        return Strings.toByte(s);
    }
    
}
