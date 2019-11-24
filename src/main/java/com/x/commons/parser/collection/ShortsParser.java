package com.x.commons.parser.collection;

import com.x.commons.parser.core.IParser;
import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.util.string.Strings;

/**
 * @Desc TODO
 * @Date 2019-11-23 20:43
 * @Author AD
 */
@Parser(result = {short[].class, Short[].class})
public class ShortsParser implements IParser<Short[], Object> {
    
    @Override
    public Short[] parse(Object o) throws Exception {
        short i = Strings.toShort(o.toString());
        // 不要用基本数据类型，遇到数组转换时，只有对象型的才可以
        return new Short[]{i};
    }
    
}