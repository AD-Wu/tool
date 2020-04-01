package com.x.commons.parser.string;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.IStringParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.date.DateTimes;

import java.time.LocalDate;

/**
 * @Desc
 * @Date 2019-11-21 22:47
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = LocalDate.class)
public class LocalDateParser extends IStringParser<LocalDate,String> {
    
    @Override
    public LocalDate parseFrom(String s) throws Exception {
        return DateTimes.toLocalDate(s);
    }
    
}
