package com.x.commons.parser.collection;

import com.x.commons.parser.core.IParser;
import com.x.commons.parser.string.annotation.Parser;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Desc
 * @Date 2019-11-23 19:49
 * @Author AD
 */
@Parser(result = Set.class)
public class SetParser implements IParser<Set<Object>, List<Object>> {
    
    @Override
    public Set<Object> parse(List<Object> list) throws Exception {
        return list.stream().collect(Collectors.toSet());
    }
    
}
