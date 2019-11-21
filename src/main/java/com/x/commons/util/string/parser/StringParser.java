package com.x.commons.util.string.parser;

import com.x.commons.util.string.Strings;
import com.x.commons.util.string.annotation.Parser;
import com.x.commons.util.string.core.IStringParser;

/**
 * @Desc TODO
 * @Date 2019-11-22 01:32
 * @Author AD
 */
@Parser(parsed = String.class)
public class StringParser implements IStringParser<String> {

    @Override
    public String parse(String s) {
        return Strings.isNull(s) ? "" : s;
    }

}
