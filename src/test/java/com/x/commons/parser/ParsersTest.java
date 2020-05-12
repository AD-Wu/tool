package com.x.commons.parser;

import com.x.commons.parser.core.IParser;
import org.junit.jupiter.api.Test;

class ParsersTest {

    @Test
    void getParser() throws Exception {
        IParser<Integer, Object> parser = Parsers.getParser(int.class);
        Integer parse = parser.parse("3");
        System.out.println(parse.intValue());
    }

}