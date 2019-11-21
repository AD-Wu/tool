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

    public static <T> IStringParser<T> getParser(Class<T> parsed) {
        return map.get(parsed);
    }

    public static boolean addParser(Class<?> result, Class<? extends IStringParser> parser) {
        try {
            map.put(result, Clazzs.newObj(parser));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void init() {
        String packageName = IntParser.class.getPackage().getName();
        List<Class<IStringParser>> parserClazzs = Clazzs.getClass(packageName, Parser.class, IStringParser.class);
        parserClazzs.forEach(clazz -> {
            Parser p = clazz.getAnnotation(Parser.class);
            Class<?> result = p.result();
            try {
                IStringParser parser = Clazzs.newObj(clazz);
                map.put(result, parser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
