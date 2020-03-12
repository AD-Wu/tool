package com.x.commons.parser.collection;

import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.string.Strings;

/**
 * @Desc TODO
 * @Date 2019-11-23 20:44
 * @Author AD
 */
@Parser(result = {long[].class, Long.class})
public class LongsParser implements IParser<Long[], Object> {
    
    @Override
    public Long[] parse(Object o) throws Exception {
        long l = Strings.toLong(o.toString());
        // 不要用基本数据类型，遇到数组转换时，只有对象型的才可以
        return new Long[]{l};
    }
    
}
