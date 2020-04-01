package com.x.commons.parser.string;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.IStringParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.string.Strings;

/**
 * @Desc
 * @Date 2019-11-22 01:32
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = String.class)
public class StringParser extends IStringParser<String,String> {
    
    @Override
    public String parseFrom(String s) throws Exception {
        return Strings.isNull(s) ? "" : s;
    }
    
}
