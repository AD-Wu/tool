package com.x.commons.parser.string.parser;

import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.parser.string.core.IStringParser;

import java.time.LocalDateTime;

/**
 * @Desc TODO
 * @Date 2019-11-21 23:00
 * @Author AD
 */
@Parser(result = LocalDateTime.class)
public class LocalDateTimeParser extends IStringParser<LocalDateTime,String> {
    
    @Override
    public LocalDateTime parseFrom(String s) throws Exception {
        LocalDateTime parse = LocalDateTime.parse(s);
        
        return parse;
    }
    
}
