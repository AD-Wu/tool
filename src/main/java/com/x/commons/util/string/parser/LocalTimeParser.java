package com.x.commons.util.string.parser;

import com.x.commons.util.date.Times;
import com.x.commons.util.string.annotation.Parser;
import com.x.commons.util.string.core.IStringParser;

import java.time.LocalTime;

/**
 * @Desc TODO
 * @Date 2019-11-21 22:58
 * @Author AD
 */
@Parser(result = LocalTime.class)
public class LocalTimeParser implements IStringParser<LocalTime> {

    @Override
    public LocalTime parse(String s) {
        return Times.to(s);
    }

}
