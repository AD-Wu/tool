package com.x.commons.util.string.parser;

import com.x.commons.util.string.Strings;
import com.x.commons.util.string.annotation.Parser;
import com.x.commons.util.string.core.IStringParser;

/**
 * @Desc TODO
 * @Date 2019-11-21 22:43
 * @Author AD
 */
@Parser(result = byte.class)
public class ByteParser implements IStringParser<Byte> {

    @Override
    public Byte parse(String s) {
        return Strings.toByte(s);
    }

}
