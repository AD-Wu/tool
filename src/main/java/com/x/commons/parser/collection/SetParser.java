package com.x.commons.parser.collection;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.Parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Desc
 * @Date 2019-11-23 19:49
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = Set.class)
public class SetParser implements IParser<Set<Object>, List<Object>> {
    
    @Override
    public Set<Object> parse(List<Object> list) throws Exception {
        return new HashSet<>(list);
    }
    
}
