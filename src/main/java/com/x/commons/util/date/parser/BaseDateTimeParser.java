package com.x.commons.util.date.parser;

import com.x.commons.parser.core.IParser;
import com.x.commons.util.date.DateTimes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @Desc 解析日期时间，子类需加注解@AutoService(BaseDateTimeParser.class)
 * @Date 2020-01-11 11:23
 * @Author AD
 */
public abstract class BaseDateTimeParser implements IParser<LocalDateTime, String> {
    
    @Override
    public LocalDateTime parse(String dateTime) throws Exception {
        return DateTimes.parse(dateTime, getPattern());
    }
    
    protected abstract String getPattern();
    
    // 不实用这种方式，pattern=yyyyMMddHHmmssSSS时解析不出来，恐有其它bug
    private LocalDateTime doParse(String s) {
        String pattern = getPattern();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        TemporalAccessor accessor = formatter.parse(s);
        LocalDateTime from = LocalDateTime.from(accessor);
        return from;
    }
    
}
