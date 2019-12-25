package com.x.commons.parser.string.parser;

import com.x.commons.util.string.Strings;
import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.parser.string.core.IStringParser;

/**
 * @Desc
 * @Date 2019-11-21 22:43
 * @Author AD
 */
@Parser(result = {double.class, Double.class})
public class DoubleParser extends IStringParser<Double, String> {
    
    @Override
    public Double parseFrom(String s) throws Exception {
        return Strings.toDouble(s);
    }
    
}
