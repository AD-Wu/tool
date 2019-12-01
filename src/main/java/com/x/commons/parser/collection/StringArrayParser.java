package com.x.commons.parser.collection;

import com.x.commons.util.string.Strings;
import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.parser.string.core.IStringParser;

/**
 * @Desc TODO
 * @Date 2019-11-22 00:10
 * @Author AD
 */
@Parser(result = String[].class)
public class StringArrayParser extends IStringParser<String[], String> {
    
    private static final String[] EMPTY = new String[0];
    
    @Override
    public String[] parseFrom(String s) throws Exception {
        if (Strings.isNull(s)) {return EMPTY;}
        return s.split("[,\\;\\|]");
    }
    
}
