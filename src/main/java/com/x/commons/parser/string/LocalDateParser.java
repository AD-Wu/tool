package com.x.commons.parser.string;

import com.x.commons.util.date.Dates;
import com.x.commons.parser.core.Parser;
import com.x.commons.parser.core.IStringParser;

import java.time.LocalDate;

/**
 * @Desc TODO
 * @Date 2019-11-21 22:47
 * @Author AD
 */
@Parser(result = LocalDate.class)
public class LocalDateParser extends IStringParser<LocalDate,String> {
    
    @Override
    public LocalDate parseFrom(String s) throws Exception {
        return Dates.to(s);
    }
    
}
