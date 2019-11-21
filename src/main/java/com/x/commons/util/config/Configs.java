package com.x.commons.util.config;

import com.x.commons.util.config.annotation.Config;
import com.x.commons.util.config.annotation.XValue;
import com.x.commons.util.convert.Strings;
import com.x.commons.util.reflact.Fields;
import com.x.commons.util.reflact.Loader;
import com.x.commons.util.reflact.Methods;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author AD
 * @Date 2019/11/21 9:37
 */
public final class Configs {

    private static final Map<Class, Object> classMap = new ConcurrentHashMap<>();

    public static <T> T get(Class<T> clazz) throws Exception {
        Config config = clazz.getAnnotation(Config.class);
        Field[] fields = Fields.getFields(clazz);
        if (config != null) {
            String path = config.path();
            InputStream in = Loader.get().getResourceAsStream(path);
            Properties prop = new Properties();
            prop.load(in);
            T target = clazz.newInstance();
            for (Field f : fields) {
                f.setAccessible(true);
                XValue msg = f.getAnnotation(XValue.class);
                Class<?> fieldClass = f.getType();
                if (msg == null) {
                    String str = prop.getProperty(f.getName());
                    Object value = parse(str, fieldClass);
                    f.set(target, value);
                } else {
                    String str = prop.getProperty(msg.key());
                    Class<?> formatClass = msg.formatClass();
                    String formatMethod = msg.formatMethod();
                    if (formatClass != null && !Strings.isNull(formatMethod)) {
                        try {
                            Object formatter = formatClass.newInstance();
                            Method method = Methods.getMethod(formatClass, formatMethod);
                            Object value = method.invoke(formatter, str);
                            f.set(target, value);
                        } catch (Exception e) {
                        }
                    } else {
                        Object value = parse(str, fieldClass);
                        f.set(target, value);
                    }
                }
            }
            return target;
        }
        return null;
    }


    private static <T> T parse(String value, Class<T> targetClass) {
        if (targetClass.equals(String.class)) {
            return (T) value;
        }
        return null;
    }

}
