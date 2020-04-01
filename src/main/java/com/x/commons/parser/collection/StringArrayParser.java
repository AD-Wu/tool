package com.x.commons.parser.collection;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.IStringParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.string.Strings;

/**
 * @Desc
 * @Date 2019-11-22 00:10
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = String[].class)
public class StringArrayParser extends IStringParser<String[], String> {
    
    private static final String[] EMPTY = new String[0];
    
    @Override
    public String[] parseFrom(String s) throws Exception {
        if (Strings.isNull(s)) {return EMPTY;}
        return s.split("[,\\;\\|]");
    }
    
}
