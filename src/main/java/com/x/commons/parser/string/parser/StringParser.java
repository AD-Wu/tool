package com.x.commons.parser.string.parser;

import com.x.commons.util.string.Strings;
import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.parser.string.core.IStringParser;

/**
 * @Desc
 * @Date 2019-11-22 01:32
 * @Author AD
 */
@Parser(result = String.class)
public class StringParser extends IStringParser<String,String> {
    
    @Override
    public String parseFrom(String s) throws Exception {
        return Strings.isNull(s) ? "" : s;
    }
    
}
