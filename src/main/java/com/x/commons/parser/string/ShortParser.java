package com.x.commons.parser.string;

import com.x.commons.util.string.Strings;
import com.x.commons.parser.core.Parser;
import com.x.commons.parser.core.IStringParser;

/**
 * @Desc TODO
 * @Date 2019-11-21 22:43
 * @Author AD
 */
@Parser(result = {short.class, Short.class})
public class ShortParser extends IStringParser<Short, String> {
    
    @Override
    public Short parseFrom(String s) throws Exception {
        return Strings.toShort(s);
    }
    
}
