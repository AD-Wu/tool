package com.x.commons.util.string.parser;

import com.x.commons.util.bean.New;
import com.x.commons.util.string.Strings;
import com.x.commons.util.string.annotation.Parser;
import com.x.commons.util.string.core.IStringParser;

import java.util.Map;

/**
 * @Desc TODO
 * @Date 2019-11-21 22:42
 * @Author AD
 */
@Parser(result = boolean.class)
public class BooleanParser implements IStringParser<Boolean> {

    @Override
    public Boolean parse(String s) {
        return Strings.toBoolean(s);
    }

}
