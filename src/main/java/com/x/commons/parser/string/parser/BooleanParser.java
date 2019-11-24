package com.x.commons.parser.string.parser;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.x.commons.util.string.Strings;
import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.parser.string.core.IStringParser;

/**
 * @Desc TODO
 * @Date 2019-11-21 22:42
 * @Author AD
 */
@Parser(result = {boolean.class, Boolean.class})
public class BooleanParser extends IStringParser<Boolean, String> {
    
    @Override
    public Boolean parseFrom(String s) throws Exception {
        return Strings.toBoolean(s);
    }
    
}
