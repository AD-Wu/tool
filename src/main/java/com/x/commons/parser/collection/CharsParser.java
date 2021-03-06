package com.x.commons.parser.collection;

import com.google.auto.service.AutoService;
import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.Parser;

/**
 * @Desc
 * @Date 2019-11-23 20:52
 * @Author AD
 */
@AutoService(IParser.class)
@Parser(result = {char[].class, Character[].class})
public class CharsParser implements IParser<Character[], Object> {
    
    @Override
    public Character[] parse(Object o) throws Exception {
        // 不要用基本数据类型，遇到数组转换时，只有对象型的才可以
        char[] chars = o.toString().toCharArray();
        Character[] cs = new Character[chars.length];
        for (int i = 0, L = chars.length; i < L; i++) {
            cs[i] = chars[i];
        }
        return cs;
    }
    
}
