package com.x.commons.parser.collection;

import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.Parser;

import java.util.Map;

/**
 * @Desc
 * @Date 2019-11-23 16:34
 * @Author AD
 */
@Parser(result = Map.class)
public class MapParser implements IParser<Map<Object,Object>, Map<Object,Object>> {
    
    @Override
    public Map<Object,Object> parse(Map<Object, Object> map) throws Exception {
        return map;
    }
    
}
