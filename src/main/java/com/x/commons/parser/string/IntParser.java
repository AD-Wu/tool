package com.x.commons.parser.string;

import com.x.commons.util.string.Strings;
import com.x.commons.parser.core.Parser;
import com.x.commons.parser.core.IStringParser;

/**
 * @Desc
 * @Date 2019-11-21 22:43
 * @Author AD
 */
@Parser(result = {int.class, Integer.class})
public class IntParser extends IStringParser<Integer, String> {
    
    @Override
    public Integer parseFrom(String s) throws Exception {
        return Strings.toInt(s);
    }
    
}
