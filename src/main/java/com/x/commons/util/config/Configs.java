package com.x.commons.util.config;

import com.x.commons.util.config.annotation.Config;
import com.x.commons.util.config.annotation.XValue;
import com.x.commons.util.convert.Strings;
import com.x.commons.util.file.Files;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.reflact.Fields;
import com.x.commons.util.reflact.Methods;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

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
        for (Field f : fields) {
            f.setAccessible(true);
            // 获取属性类型
            Class<?> fieldClass = f.getType();
            // 获取注解信息
            XValue msg = f.getAnnotation(XValue.class);
            // 没有@XValue注解
            if (msg == null) {
                setValueWithoutAnnotation(prop, prefix, f, fieldClass, target);
            } else {
                setValueWithAnnotation(prop, msg, prefix, f, fieldClass, target);
            }
        }
        return target;
    }
    private static <T> T parse(String value, Class<T> targetClass) {
        if (value == null) {
            return (T) "";
        }
        if (targetClass.equals(String.class)) {
            return (T) value;
        }
        return null;
    }
    private static void setValueWithoutAnnotation(Properties prop, String prefix, Field field, Class<?> fieldClass, Object target) throws Exception {
        // 获取值
        String str = prop.getProperty(prefix + field.getName());
        // 解析成对应类型
        Object value = parse(str, fieldClass);
        // 设置
        field.set(target, value);
    }

    private static void setValueWithAnnotation(Properties prop, XValue msg, String prefix, Field field, Class<?> fieldClass, Object target) throws Exception {
        // 获取值
        String param = prop.getProperty(prefix + msg.key());
        // 特定格式化
        if (needFormat(msg)) {
            // 反射调用获取格式化之后的值
            Object formatter = Clazzs.newObj(msg.formatClass());
            Method method = Methods.getMethod(msg.formatClass(), msg.methodName());
            Object value = method.invoke(formatter, param);
            // 设置值
            field.set(target, value);
        } else {
            // 解析设置
            Object value = parse(param, fieldClass);
            field.set(target, value);
        }
    }

    private static boolean needFormat(XValue msg) {
        return msg.formatClass() != null && !Strings.isNull(msg.methodName());
    }

    private static String fixPrefix(String prefix) {
        return Strings.isNull(prefix) ? "" : (prefix.endsWith(".") ? prefix : prefix + ".");
    }


}
