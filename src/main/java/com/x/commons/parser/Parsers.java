package com.x.commons.parser;

import com.x.commons.parser.core.IParser;
import com.x.commons.parser.core.Parser;
import com.x.commons.util.bean.New;
import com.x.commons.util.reflact.Clazzs;

import java.util.Iterator;
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
                    inited=true;
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
     * 初始化解析类
     */
    private static void init() {
        parsers = New.map();
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
