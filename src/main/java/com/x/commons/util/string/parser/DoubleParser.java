package com.x.commons.util.string.parser;

import com.x.commons.util.string.Strings;
import com.x.commons.util.string.annotation.Parser;
import com.x.commons.util.string.core.IStringParser;

/**
 * @Desc TODO
 * @Date 2019-11-21 22:43
 * @Author AD
 */
@Parser(result = double.class)
public class DoubleParser implements IStringParser<Double> {

    @Override
    public Double parse(String s) {
        return Strings.toDouble(s);
    }

}
