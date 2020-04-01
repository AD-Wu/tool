package com.x.commons.parser.collection;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.reflact.Clazzs;

/**
 * @Desc
 * @Date 2019-11-23 19:50
 * @Author AD
 */
@AutoService(IParser.class)
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
