package com.x.commons.parser.string;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.IStringParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.date.DateTimes;

import java.time.LocalDateTime;

/**
 * @Desc
 * @Date 2019-11-21 23:00
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = LocalDateTime.class)
public class LocalDateTimeParser extends IStringParser<LocalDateTime,String> {
    
    @Override
    public LocalDateTime parseFrom(String s) throws Exception {
        return DateTimes.toLocalDateTime(s);
    }
    
}
