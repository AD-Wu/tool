package com.x.commons.util.config;

import com.x.commons.util.config.annotation.Config;
import com.x.commons.util.config.annotation.XValue;
import com.x.commons.util.string.Strings;
import com.x.commons.util.file.Files;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.reflact.Fields;
import com.x.commons.util.reflact.Methods;
import com.x.commons.util.string.core.IStringParser;
import com.x.commons.util.string.core.StringParsers;
import com.x.commons.util.string.enums.StringParser;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @Author AD
 * @Date 2019/11/21 9:37
 */
public final class Configs {

    private static final Map<Class, Object> classMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        User user = get(User.class);
        System.out.println(user);
    }

    // ---------------------- 成员方法 ---------------------

    public static <T> T get(Class<T> clazz) throws Exception {
        Config config = clazz.getAnnotation(Config.class);
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
            // 没有@XValue注解
            if (msg == null) {
                setValueWithoutAnnotation(prop, prefix, f, parsed, target);
            } else {
                setValueWithAnnotation(prop, msg, prefix, f, parsed, target);
            }
        });
        return target;
    }

    // ---------------------- 私有方法 ---------------------

    private static void setValueWithoutAnnotation(Properties prop, String prefix, Field field, Class<?> parsed,
                                                  Object target) {
        // 获取值
        String str = prop.getProperty(prefix + field.getName());
        // 解析值
        Object value = StringParsers.getParser(parsed).parse(str);
        try {
            // 设置值
            field.set(target, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setValueWithAnnotation(Properties prop, XValue msg, String prefix, Field field,
                                               Class<?> parsed, Object target) {
        // 获取值
        String param = prop.getProperty(prefix + msg.key());
        // 特定格式化
        if (needFormat(msg)) {
            // 注册特定的解析器
            StringParsers.addParser(parsed, msg.parser());
        }
        // 获取值
        Object value = StringParsers.getParser(parsed).parse(param);
        try {
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

}
