package com.x.commons.parser.collection;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.string.Strings;

/**
 * @Desc
 * @Date 2019-11-23 20:50
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = {float[].class,Float[].class})
public class FloatsParser implements IParser<Float[], Object> {
    @Override
    public Float[] parse(Object o) throws Exception {
        float v = Strings.toFloat(o.toString());
        // 不要用基本数据类型，遇到数组转换时，只有对象型的才可以
        return new Float[]{v};
    }
}