package com.x.commons.util.string.core;

import com.x.commons.util.bean.New;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.string.annotation.Parser;
import com.x.commons.util.string.parser.IntParser;

import java.util.List;
import java.util.Map;

/**
 * @Desc TODO
 * @Date 2019-11-21 23:08
 * @Author AD
 */
public final class StringParsers {

    private static final Map<Class, IStringParser> map = New.concurrentMap();

    static {
        init();
    }

    public static IStringParser getParser(Class<?> parsed) {
        return map.get(parsed);
    }

    public static boolean addParser(Class<?> parsed, Class<? extends IStringParser> parser) {
        try {
            map.put(parsed, Clazzs.newObj(parser));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void init() {
        String packageName = IntParser.class.getPackage().getName();
        List<Class<IStringParser>> parsersClass = Clazzs.getClass(packageName, Parser.class, IStringParser.class);
        parsersClass.forEach(parserClass -> {
            Parser p = parserClass.getAnnotation(Parser.class);
            Class<?> parsed = p.parsed();
            try {
                IStringParser parser = Clazzs.newObj(parserClass);
                map.put(parsed, parser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
