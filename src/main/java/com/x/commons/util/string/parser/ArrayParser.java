package com.x.commons.util.string.parser;

import com.x.commons.util.string.Strings;
import com.x.commons.util.string.annotation.Parser;
import com.x.commons.util.string.core.IStringParser;

/**
 * @Desc TODO
 * @Date 2019-11-22 00:10
 * @Author AD
 */
@Parser(result = String[].class)
public class ArrayParser implements IStringParser<String[]> {

    private static final String[] EMPTY = new String[0];

    @Override
    public String[] parse(String s) {
        if (Strings.isNull(s)) {return EMPTY;}
        if (s.contains(",")) {
            return s.split(",");
        } else {
            if (s.contains("|")) {
                return s.split("|");
            } else {
                return s.split(" ");
            }
        }
    }

}
