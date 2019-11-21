package com.x.commons.util.string.parser;

import com.x.commons.util.string.Strings;
import com.x.commons.util.string.annotation.Parser;
import com.x.commons.util.string.core.IStringParser;

/**
 * @Desc TODO
 * @Date 2019-11-21 22:43
 * @Author AD
 */
@Parser(parsed = long.class)
public class LongParser implements IStringParser<Long> {

    @Override
    public Long parse(String s) {
        return Strings.toLong(s);
    }

}
