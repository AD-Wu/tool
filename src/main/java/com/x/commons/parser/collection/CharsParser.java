package com.x.commons.parser.collection;

import com.x.commons.parser.core.IParser;
import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.util.string.Strings;

import java.util.Arrays;

/**
 * @Desc TODO
 * @Date 2019-11-23 20:52
 * @Author AD
 */
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
