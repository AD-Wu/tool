package com.x.commons.parser.string;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.IStringParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.string.Strings;

/**
 * @Desc
 * @Date 2019-11-21 22:43
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = {double.class, Double.class})
public class DoubleParser extends IStringParser<Double, String> {
    
    @Override
    public Double parseFrom(String s) throws Exception {
        return Strings.toDouble(s);
    }
    
}
