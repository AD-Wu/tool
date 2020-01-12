package com.x.commons.parser.string.parser;

import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.parser.string.core.IStringParser;
import com.x.commons.util.date.DateTimes;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Desc TODO
 * @Date 2019-11-22 00:02
 * @Author AD
 */
@Parser(result = Date.class)
public class DateParser extends IStringParser<Date, String> {
    
    @Override
    public Date parseFrom(String s) throws Exception {
        LocalDateTime dateTime = DateTimes.autoParse(s);
        return dateTime == null ? null : DateTimes.toDate(dateTime);
    }
    
}
