package com.x.commons.parser.collection;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.string.Strings;

/**
 * @Desc
 * @Date 2019-11-23 20:49
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = {double[].class, Double[].class})
public class DoublesParser implements IParser<Double[], Object> {
    
    @Override
    public Double[] parse(Object o) throws Exception {
        double v = Strings.toDouble(o.toString());
        // 不要用基本数据类型，遇到数组转换时，只有对象型的才可以
        return new Double[]{v};
    }
    
}