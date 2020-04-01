package com.x.commons.parser.collection;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.Parser;

import java.util.List;

/**
 * @Desc
 * @Date 2019-11-23 19:42
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = List.class)
public class ListParser implements IParser<List<Object>, List<Object>> {
    
    @Override
    public List<Object> parse(List<Object> list) throws Exception {
        return list;
    }
    
}
