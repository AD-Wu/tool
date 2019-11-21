package com.x.commons.util.string.parser;

import com.x.commons.util.date.DateTimes;
import com.x.commons.util.string.annotation.Parser;
import com.x.commons.util.string.core.IStringParser;

import java.time.LocalDateTime;

/**
 * @Desc TODO
 * @Date 2019-11-21 23:00
 * @Author AD
 */
@Parser(result = LocalDateTime.class)
public class LocalDateTimeParser implements IStringParser<LocalDateTime> {

    @Override
    public LocalDateTime parse(String s) {
        return DateTimes.to(s);
    }

}
