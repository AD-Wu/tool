package com.x.commons.parser.string.parser;

import com.x.commons.util.string.Strings;
import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.parser.string.core.IStringParser;

/**
 * @Desc TODO
 * @Date 2019-11-21 22:43
 * @Author AD
 */
@Parser(result = {long.class, Long.class})
public class LongParser extends IStringParser<Long, String> {
    
    @Override
    public Long parseFrom(String s) throws Exception {
        return Strings.toLong(s);
    }
    
}
