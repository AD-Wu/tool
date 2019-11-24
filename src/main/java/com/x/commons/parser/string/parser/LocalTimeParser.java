package com.x.commons.parser.string.parser;

import com.x.commons.util.date.Times;
import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.parser.string.core.IStringParser;

import java.time.LocalTime;

/**
 * @Desc TODO
 * @Date 2019-11-21 22:58
 * @Author AD
 */
@Parser(result = LocalTime.class)
public class LocalTimeParser extends IStringParser<LocalTime,String> {
    
    @Override
    public LocalTime parseFrom(String s) throws Exception {
        return Times.to(s);
    }
    
}
