package com.x.commons.util.prop;

import com.x.commons.parser.Parsers;
import com.x.commons.parser.string.core.IStringParser;
import com.x.commons.util.file.Files;
import com.x.commons.util.prop.annotation.Prop;
import com.x.commons.util.prop.annotation.XValue;
import com.x.commons.util.prop.test.User;
import com.x.commons.util.reflact.Fields;
import com.x.commons.util.string.Strings;

import java.lang.reflect.Field;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * xxx.properties配置文件工具类
 *
 * @Author AD
 * @Date 2019/11/21 9:37
 */
public final class Props {
    
    // ------------------------ 构造方法 ------------------------
    
    private Props() {}
    
    // ---------------------- 成员方法 ---------------------
    
    /**
     * 将xxx.properties的配置文件解析成class
     *
     * @param clazz 加了@Prop注解的类，该类属性可以配合@XValue使用
     * @param <T>   目标类型
     *
     * @return 目标类对象
     *
     * @throws Exception
     */
    public static <T> T get(Class<T> clazz) throws Exception {
        Prop config = clazz.getAnnotation(Prop.class);
        if (config == null) {
            return null;
        }
        // 获取路径解析文件
        Properties prop = Files.toProperties(config.path());
        // 获取前缀
        String prefix = fixPrefix(config.prefix());
        // 反射出所有的属性
        Field[] fields = Fields.getFields(clazz);
        // 创建对象
        T target = clazz.newInstance();
        // 循环赋值
        Stream.of(fields).forEach(f -> {
            f.setAccessible(true);
            // 获取属性类型
            Class<?> parsed = f.getType();
            // 获取注解信息
            XValue msg = f.getAnnotation(XValue.class);
            try {
                // 没有@XValue注解
                if (msg == null) {
                    setValueWithoutAnnotation(prop, prefix, f, parsed, target);
                } else {
                    setValueWithAnnotation(prop, msg, prefix, f, parsed, target);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        });
        return target;
    }
    
    // ---------------------- 私有方法 ---------------------
    
    /**
     * 有注解@XValue的，通过注解解析
     *
     * @param prop   xxx.properties 文件通过流加载后的对象
     * @param prefix 前缀
     * @param field  类的成员属性
     * @param parsed 被解析field属性类型
     * @param target 被解析的属性所对应的对象
     *
     * @throws Exception
     */
    private static void setValueWithoutAnnotation(
            Properties prop,
            String prefix,
            Field field,
            Class<?> parsed,
            Object target) throws Exception {
        // 获取值
        String str = prop.getProperty(prefix + field.getName());
        // 解析值
        Object value = Parsers.getParser(parsed).parse(str);
        // 设置值
        field.set(target, value);
        
    }
    
    private static void setValueWithAnnotation(
            Properties prop,
            XValue msg,
            String prefix,
            Field field,
            Class<?> parsed,
            Object target) {
        // 获取值
        String param = prop.getProperty(prefix + msg.key());
        // 特定格式化
        if (needFormat(msg)) {
            // 注册特定的解析器
            Parsers.addParser(parsed, msg.parser());
        }
        try {
            // 获取值
            Object value = Parsers.getParser(parsed).parse(param);
            // 设置值
            field.set(target, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private static boolean needFormat(XValue msg) {
        Class<? extends IStringParser> parser = msg.parser();
        return parser != null && parser != IStringParser.class && IStringParser.class.isAssignableFrom(parser);
    }
    
    private static String fixPrefix(String prefix) {
        return Strings.isNull(prefix) ? "" : (prefix.endsWith(".") ? prefix : prefix + ".");
    }
    
    public static void main(String[] args) throws Exception {
        User user = get(User.class);
        System.out.println(user);
    }
    
}
