package com.x.commons.util.string.parser;

import com.x.commons.util.date.Dates;
import com.x.commons.util.string.Strings;
import com.x.commons.util.string.annotation.Parser;
import com.x.commons.util.string.core.IStringParser;

import java.time.LocalDate;

/**
 * @Desc TODO
 * @Date 2019-11-21 22:47
 * @Author AD
 */
@Parser(result = LocalDate.class)
public class LocalDateParser implements IStringParser<LocalDate> {

    @Override
    public LocalDate parse(String s) {
        return Dates.to(s);
    }

}
