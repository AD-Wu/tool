package com.x.commons.parser.collection;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.string.Strings;

/**
 * @Desc
 * @Date 2019-11-23 20:39
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = {int[].class, Integer.class})
public class IntsParser implements IParser<Integer[], Object> {
    
    @Override
    public Integer[] parse(Object o) throws Exception {
        int i = Strings.toInt(o.toString());
        // 不要用基本数据类型，遇到数组转换时，只有对象型的才可以
        return new Integer[]{i};
    }
    
}
