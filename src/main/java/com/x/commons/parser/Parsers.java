package com.x.commons.parser;

import com.x.commons.parser.core.IParser;
import com.x.commons.parser.string.annotation.Parser;
import com.x.commons.util.bean.New;
import com.x.commons.util.reflact.Clazzs;

import java.util.List;
import java.util.Map;

/**
 * @Desc 解析类工具
 * @Desc 指定解析类：在resource/x-framework/parser.xml进行配置
 * @Date 2019-11-21 23:08
 * @Author AD
 */
public final class Parsers {
    
    // ------------------------ 成员变量 ------------------------
    
    private static final Map<Class, IParser> map = New.concurrentMap();
    
    private static final String PATH = "x-framework/parser.xml";
    
    // ------------------------ 构造方法 ------------------------
    
    private Parsers() {}
    
    // ------------------------ 成员方法 ------------------------
    
    public static <R, S> IParser<R, S> getParser(Class<R> resultClass) {
        IParser parser = map.get(resultClass);
        return parser;
        
    }
    
    public static boolean addParser(Class<?> result, Class<? extends IParser> parser) {
        try {
            map.put(result, Clazzs.newInstance(parser));
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
                    map.put(result, parser);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    /**
     * 初始化自定义解析类
     */
    private static void initCustom() {
    
    }
    
    static {
        init();
        initCustom();
    }
    
}
