package com.x.commons.parser.string;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.util.string.Strings;
import com.x.commons.parser.core.Parser;
import com.x.commons.parser.core.IStringParser;

/**
 * @Desc
 * @Date 2019-11-21 22:42
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = {boolean.class, Boolean.class})
public class BooleanParser extends IStringParser<Boolean, String> {
    
    @Override
    public Boolean parseFrom(String s) throws Exception {
        return Strings.toBoolean(s);
    }
    
}
