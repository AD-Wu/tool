package com.x.commons.parser.string;

import com.x.commons.parser.core.Parser;
import com.x.commons.parser.core.IStringParser;
import com.x.commons.util.date.DateTimes;

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
        return DateTimes.autoParse(s);
    }
    
}
