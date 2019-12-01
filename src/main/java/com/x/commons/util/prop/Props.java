package com.x.commons.util.prop;

import com.x.commons.database.pool.PoolConfig;
import com.x.commons.parser.Parsers;
import com.x.commons.parser.string.core.IStringParser;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.collection.Maps;
import com.x.commons.util.file.Files;
import com.x.commons.util.prop.annotation.Prop;
import com.x.commons.util.prop.annotation.XValue;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.reflact.Fields;
import com.x.commons.util.string.Strings;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
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
    public static Map<String, String> load(String path) throws Exception {
        /**
         * 读取路径文件的必须用文件流，适用于绝对和相对路径，ClassLoader只使用相对路径
         * fin和reader不能分开，如果fin关闭，则reader读不到
         */
        try (FileInputStream fin = new FileInputStream(path);
             InputStreamReader reader = Files.getUnicodeReader(fin);) {
            Properties prop = new Properties();
            prop.load(reader);
            return Maps.toMap(prop);
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * 将类转为xxx.properties的文件（resources/x-framework）
     *
     * @param clazz 需要转换的类，如数据库连接池的配置文件
     *
     * @return
     *
     * @throws Exception
     */
    public static boolean to(Class<?> clazz) throws Exception {
        if (clazz == null) {
            return false;
        }
        Object instance = Clazzs.newInstance(clazz);
        String filename = clazz.getSimpleName();
        SB sb = New.sb();
        // 获取所有的属性
        Field[] fields = Fields.getFields(clazz);
        // 获取属性名=值
        Arrays.stream(fields).forEach(f -> {
            f.setAccessible(true);
            String key = f.getName();
            try {
                Object value = f.get(instance);
                value = value == null ? "" : value;
                sb.append(key).append("=").append(value).append("\r\n");
            } catch (IllegalAccessException e) {
            }
        });
        String folder = Files.getResourcesPath() + "x-framework";
        return Files.createFile(folder, filename + ".properties", sb.get());
    }
    
    /**
     * 将xxx.properties的配置文件解析成class
     *
     * @param clazz 加了@Prop(path="",prefix="")注解的类，该类属性可以配合@XValue使用
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
        // User user = get(User.class);
        // System.out.println(user);
        boolean to = to(PoolConfig.class);
        System.out.println(to);
    }
    
}
