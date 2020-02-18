package com.x.commons.parser;

import com.x.commons.parser.core.IParser;
import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.util.bean.New;
import com.x.commons.util.log.Logs;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.string.Strings;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @Desc 解析类工具
 * @Date 2019-11-21 23:08
 * @Author AD
 */
public final class Parsers {
    
    // ------------------------ 成员变量 ------------------------
    
    private static Map<Class, IParser> parsers;
    
    private static volatile boolean inited = false;
    
    private static final Object LOCK = new Object();
    
    // ------------------------ 构造方法 ------------------------
    
    private Parsers() {}
    
    // ------------------------ 成员方法 ------------------------
    
    public static <R, S> IParser<R, S> getParser(Class<R> returnType) {
        if (!inited) {
            synchronized (LOCK) {
                if (!inited) {
                    init();
                    initCustom();
                }
            }
        }
        return parsers.get(returnType);
    }
    
    public static boolean addParser(Class<?> result, Class<? extends IParser> parser) {
        try {
            parsers.put(result, Clazzs.newInstance(parser));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ------------------------ 私有方法 ------------------------
    
    /**
     * 从com.x.commons.util.string.parser包中初始化解析类
     */
    private static void init() {
        parsers = New.map();
        // 获取包下所有加@Parser和实现IParser接口的类
        String packageName = Parsers.class.getPackage().getName();
        List<Class<IParser>> parserClazzs = Clazzs.getClass(packageName, Parser.class, IParser.class);
        // key:结果类型，value:parser类对象
        parserClazzs.forEach(clazz -> {
            Parser p = clazz.getAnnotation(Parser.class);
            Class<?>[] results = p.result();
            try {
                for (Class<?> result : results) {
                    IParser parser = Clazzs.newInstance(clazz);
                    parsers.put(result, parser);
                }
            } catch (Exception e) {
                Logs.get(Parsers.class).error(Strings.getExceptionTrace(e));
            }
        });
        inited = true;
    }
    
    /**
     * 初始化自定义解析类
     */
    private static void initCustom() {
        // 获取接口的实现类，需要在实现类上加Google的@AutoService
        ServiceLoader<IParser> load = ServiceLoader.load(IParser.class);
        Iterator<IParser> it = load.iterator();
        while (it.hasNext()) {
            IParser parser = it.next();
            Class<? extends IParser> subClass = parser.getClass();
            if (subClass.isAnnotationPresent(Parser.class)) {
                // 根据@Parser注解获取返回值类型
                Parser p = subClass.getAnnotation(Parser.class);
                Class<?>[] returnType = p.result();
                for (Class<?> type : returnType) {
                    parsers.put(type, parser);
                }
            }
        }
    }
    
}
