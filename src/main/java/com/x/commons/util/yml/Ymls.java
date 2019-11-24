package com.x.commons.util.yml;

import com.x.commons.parser.core.IParser;
import com.x.commons.util.bean.New;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.reflact.Fields;
import com.x.commons.util.reflact.Loader;
import com.x.commons.parser.Parsers;
import com.x.commons.util.string.Strings;
import com.x.commons.util.yml.test.Config;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * @Desc TODO
 * @Date 2019-11-23 10:08
 * @Author AD
 */
public final class Ymls {
    
    // ------------------------ 构造方法 ------------------------
    
    private Ymls() {}
    
    // ------------------------ 成员方法 ------------------------
    
    /**
     * 将yml/yaml文件解析成对象
     *
     * @param path yml/yaml文件路径，如：resources/x-framework/x.yml
     */
    public static <T> T load(String path, Class<T> clazz) throws Exception {
        return load(path, clazz, "");
    }
    
    /**
     * 将yml/yaml文件解析成对象，可指定前缀，效率高
     *
     * @param path   yml/yaml文件路径，如：resources/x-framework/x.yml
     * @param clazz
     * @param prefix yml/yaml的某个前缀
     * @param <T>
     *
     * @return
     *
     * @throws Exception
     */
    public static <T> T load(String path, Class<T> clazz, String prefix) throws Exception {
        // 拿到map结构->String=LinkedHashMap
        Map<String, Object> props = load(path);
        // 有前缀效率高
        props = findProps(prefix, props);
        // 反射创建对象
        T t = clazz.newInstance();
        // 拿到类的所有属性
        Field[] fields = Fields.getFields(clazz);
        // 将属性添加到集合
        Map<String, Field> fieldMap = New.map();
        Map<String, Class> fieldClassMap = New.map();
        Stream.of(fields).forEach(f -> {
            fieldMap.put(f.getName(), f);
            fieldClassMap.put(f.getName(), f.getType());
        });
        setValue(t, props, fieldMap, fieldClassMap);
        return t;
    }
    
    public static Map<String, Object> load(String path) throws Exception {
        try (InputStream in = Loader.get().getResourceAsStream(path);) {
            Yaml yaml = new Yaml();
            Map<String, Object> load = yaml.load(in);
            return load;
        } catch (Exception e) {
            throw e;
        }
    }
    
    // ------------------------ 私有方法 ------------------------
    
    private static <T> void setValue(
            T t,
            Map<String, Object> props,
            Map<String, Field> fieldMap,
            Map<String, Class> fieldClassMap) throws Exception {
        
        props.entrySet().forEach(prop -> {
            String key = prop.getKey();
            // 一级目录
            if (fieldMap.containsKey(key)) {
                Class fieldClass = fieldClassMap.get(key);
                Object value = null;
                try {
                    // 先判断是否有指定解析器
                    IParser parser = Parsers.getParser(fieldClass);
                    if (parser != null) {
                        value = parser.parse(prop.getValue());
                    } else {
                        // 判断是否对象类型（非数组）
                        if (Clazzs.isObject(fieldClass)) {
                            Map<String, Object> subProp = (Map<String, Object>) prop.getValue();
                            // parser完善后，此方法基本没用，留着以防万一
                            value = parseObject(fieldClass, subProp);
                        }
                    }
                    Field field = fieldMap.get(key);
                    field.setAccessible(true);
                    /**
                     *  将数组转换成目标数组类型
                     *  long[]和Long[]不能互相设置，
                     *  long[]不能强制转换成Object[],Long[]才可以
                     */
                    if (Clazzs.isArray(value.getClass())) {
                        Object o = toTargetArray(fieldClass, value);
                        field.set(t, o);
                    } else {
                        field.set(t, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // 二级目录
                Object o = props.get(key);
                if (o instanceof Map) {
                    Map<String, Object> subMap = (Map<String, Object>) o;
                    try {
                        setValue(t, subMap, fieldMap, fieldClassMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    
    /**
     * 解析对象，parser 完善后基本没用
     *
     * @param clazz 需要解析的类，不能是内部类
     * @param map   从yml/yaml解析出来的源数据
     *
     * @return
     *
     * @throws Exception
     */
    private static Object parseObject(Class<?> clazz, Map<String, Object> map) throws Exception {
        Object o = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        Stream.of(fields).forEach(f -> {
            f.setAccessible(true);
            Class<?> fieldClass = f.getType();
            Object value = map.get(f.getName());
            try {
                IParser parser = Parsers.getParser(fieldClass);
                if (parser != null) {
                    value = parser.parse(value);
                } else {
                    if (Clazzs.isObject(fieldClass)) {
                        value = parseObject(fieldClass, (Map<String, Object>) value);
                    }
                }
                f.set(o, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return o;
    }
    
    private static Object toTargetArray(Class<?> valueClass, Object value) {
        if (Clazzs.isArray(valueClass)) {
            Class<?> type = valueClass.getComponentType();
            Object[] os = (Object[]) value;
            int length = os.length;
            Object instance = Array.newInstance(type, length);
            for (int i = 0, L = os.length; i < L; i++) {
                Array.set(instance, i, os[i]);
            }
            return instance;
        }
        return null;
    }
    
    private static Map<String, Object> findProps(String key, Map<String, Object> props) {
        if (!Strings.isNull(key)) {
            Set<String> keys = props.keySet();
            if (keys.contains(key)) {
                return (Map<String, Object>) props.get(key);
            } else {
                Iterator<Map.Entry<String, Object>> it = props.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> next = it.next();
                    Object o = next.getValue();
                    if (o instanceof Map) {
                        Map<String, Object> value = (Map<String, Object>) o;
                        return findProps(key, value);
                    }
                }
                
            }
        }
        return props;
    }
    
    public static void main(String[] args) throws Exception {
        Config load = load("x-framework/yml/test.yml", Config.class);
        System.out.println(load);
    }
    
}
