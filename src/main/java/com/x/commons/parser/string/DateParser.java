package com.x.commons.parser.string;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.IStringParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.date.DateTimes;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Desc
 * @Date 2019-11-22 00:02
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = Date.class)
public class DateParser extends IStringParser<Date, String> {
    
    @Override
    public Date parseFrom(String s) throws Exception {
        LocalDateTime dateTime = DateTimes.toLocalDateTime(s);
        return dateTime == null ? null : DateTimes.toDate(dateTime);
    }
    
}
