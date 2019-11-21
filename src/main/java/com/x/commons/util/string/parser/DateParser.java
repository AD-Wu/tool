package com.x.commons.util.string.parser;

import com.x.commons.util.string.Strings;
import com.x.commons.util.string.annotation.Parser;
import com.x.commons.util.string.core.IStringParser;

import java.util.Date;

/**
 * @Desc TODO
 * @Date 2019-11-22 00:02
 * @Author AD
 */
@Parser(result = Date.class)
public class DateParser implements IStringParser<Date> {

    @Override
    public Date parse(String s) {
        return Strings.isDateOrTime(s) ? new Date(s) : null;
    }

}
