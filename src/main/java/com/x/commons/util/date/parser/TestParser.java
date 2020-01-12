package com.x.commons.util.date.parser;

import com.google.auto.service.AutoService;

/**
 * @Desc TODO
 * @Date 2020-01-12 11:09
 * @Author AD
 */
@AutoService(BaseDateTimeParser.class)
public class TestParser extends BaseDateTimeParser {
    
    @Override
    protected String getPattern() {
        return "yyyy|MM|dd|HH|mm|ss|SSS";
    }
    
}
