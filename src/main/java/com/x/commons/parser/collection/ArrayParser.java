package com.x.commons.parser.collection;

import com.x.commons.parser.core.IParser;
import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.util.reflact.Clazzs;

import java.util.Arrays;
import java.util.List;

/**
 * @Desc TODO
 * @Date 2019-11-23 19:50
 * @Author AD
 */
@Parser(result = Object[].class)
public class ArrayParser implements IParser<Object[], Object> {
    
    @Override
    public Object[] parse(Object o) throws Exception {
        if (o == null) {
            return new Object[0];
        }
        if (Clazzs.isArray(o.getClass())) {
            return (Object[]) o;
        } else {
            return new Object[]{o};
        }
    }
    
}
