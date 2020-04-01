package com.x.commons.parser.string;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.IStringParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.date.DateTimes;

import java.time.LocalTime;

/**
 * @Desc
 * @Date 2019-11-21 22:58
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = LocalTime.class)
public class LocalTimeParser extends IStringParser<LocalTime,String> {
    
    @Override
    public LocalTime parseFrom(String s) throws Exception {
        return DateTimes.toLocalTime(s);
    }
    
}
