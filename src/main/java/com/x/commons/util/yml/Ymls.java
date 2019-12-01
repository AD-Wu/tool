package com.x.commons.util.yml;

import com.x.commons.parser.Parsers;
import com.x.commons.parser.core.IParser;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.collection.Arrays;
import com.x.commons.util.file.Files;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.reflact.Fields;
import com.x.commons.util.string.Strings;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @Desc TODO
 * @Date 2019-11-23 10:08
 * @Author AD
 */
public final class Ymls {
    
    // 测试用
    private static final String YML_PATH = "x-framework/yml/test.yml";
    
    // ------------------------ 构造方法 ------------------------
    
    private Ymls() {
    }
    
    // ------------------------ 成员方法 ------------------------
    
    /**
     * 将yml/yaml文件解析成对象
     *
     * @param path  yml/yaml文件路径，如：resources/x-framework/x.yml
     * @param clazz 想把yml/yaml解析成POJO的对象类型
     * @param <T>
     *
     * @return
     *
     * @throws Exception
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
        props = findPropsByPrefix(prefix, props);
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
    
    /**
     * 将yml/yaml文件转为properties格式的文件
     *
     * @param path yml/yaml 文件路径
     *
     * @return
     *
     * @throws Exception
     */
    public static Map<String, String> loadAsFlatMap(String path) throws Exception {
        Map<String, Object> load = load(path);
        Map<String, String> result = New.map();
        // 递归解析
        flat(load, "", result);
        return result;
    }
    
    // ------------------------ 私有方法 ------------------------
    
    /**
     * 原生的yaml结构解析，String=LinkedHashMap<String,LinkedHashMap>
     *
     * @param path yml/yaml文件路径
     *
     * @return
     *
     * @throws Exception
     */
    private static Map<String, Object> load(String path) throws Exception {
        /**
         * 读取路径文件的必须用文件流，适用于绝对和相对路径，ClassLoader只使用相对路径
         * fin和reader不能分开，如果fin关闭，则reader读不到
         */
        try (FileInputStream fin = new FileInputStream(path);
             InputStreamReader reader = Files.getUnicodeReader(fin);) {
            Yaml yaml = new Yaml();
            Map<String, Object> load = yaml.load(reader);
            return load;
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * 将load()方法加载的map<String,LinkedHashMap>递归解析成Map<String,String>,即扁平化
     *
     * @param map       load()加载的结果
     * @param supPrefix 上一级前缀
     * @param result    递归所需的map
     */
    private static void flat(Map<String, Object> map, String supPrefix, Map<String, String> result) {
        map.entrySet().forEach(e -> {
            String key = e.getKey();
            String fixKey = supPrefix + key;
            Object value = e.getValue();
            if (value instanceof Map) {
                fixKey = fixKey + ".";
                flat((Map<String, Object>) value, fixKey, result);
            } else if (value instanceof List) {
                List<String> list = (List<String>) value;
                SB sb = New.sb();
                for (String s : list) {
                    sb.append(s).append(",");
                }
                result.put(fixKey, sb.deleteLast().get());
            } else {
                result.put(fixKey, value.toString());
            }
        });
    }
    
    /**
     * 反射机制设置Field的值
     *
     * @param t             Field所在类对象
     * @param props         从yml/yaml解析出来的源数据
     * @param fieldMap      fieldName-Field的映射
     * @param fieldClassMap fidle name-field class的映射
     * @param <T>
     *
     * @throws Exception
     */
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
                    // 设置属性可访问
                    Field field = fieldMap.get(key);
                    field.setAccessible(true);
                    /**
                     *  将数组转换成目标数组类型
                     *  long[]和Long[]不能互相设置，
                     *  long[]不能强制转换成Object[],Long[]才可以
                     */
                    if (Clazzs.isArray(value.getClass())) {
                        Object o = Arrays.convert(value, fieldClass);
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
        // 反射创建对象
        Object o = Clazzs.newInstance(clazz);
        // 获取所有属性
        Field[] fields = clazz.getDeclaredFields();
        // 遍历属性
        Stream.of(fields).forEach(f -> {
            // 设置可访问
            f.setAccessible(true);
            // 获取属性类型
            Class<?> fieldClass = f.getType();
            // 从源数据根据name获取值
            Object value = map.get(f.getName());
            try {
                // 根据field类型获取解析器
                IParser parser = Parsers.getParser(fieldClass);
                // 不为空就按照解析器解析
                if (parser != null) {
                    value = parser.parse(value);
                } else {
                    // 判断field是否为对象型
                    if (Clazzs.isObject(fieldClass)) {
                        // 递归解析改对象型的Field
                        value = parseObject(fieldClass, (Map<String, Object>) value);
                    }
                }
                // 设置属性值
                f.set(o, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return o;
    }
    
    /**
     * 通过前缀找到前缀下的子内容
     *
     * @param prefix 需查找内容的前缀
     * @param props  从yml/yaml所加载的源数据
     *
     * @return Map<String                               ,                                                               LinkedHashMap                                                               <                                                               String                               ,                                                               Map>>
     * 源数据的某个子结构
     */
    private static Map<String, Object> findPropsByPrefix(String prefix, Map<String, Object> props) {
        if (!Strings.isNull(prefix)) {
            // 获取所有的key
            Set<String> keys = props.keySet();
            // 如果一级目录包括目标key，直接返回
            if (keys.contains(prefix)) {
                return (Map<String, Object>) props.get(prefix);
            } else {
                // 二级查找（这里不能用Stream循环解析）
                Iterator<Map.Entry<String, Object>> it = props.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> next = it.next();
                    Object o = next.getValue();
                    // 如果是map，则递归解析
                    if (o instanceof Map) {
                        Map<String, Object> value = (Map<String, Object>) o;
                        return findPropsByPrefix(prefix, value);
                    }
                }
                
            }
        }
        return props;
    }
    
    public static void main(String[] args) throws Exception {
        // Config load = load("x-framework/yml/test.yml", Config.class);
        Map<String, Object> load = load("x-framework/yml/test.yml");
        // System.out.println(load);
        Yaml yaml = new Yaml();
        
        String dump = yaml.dump(load);
        System.out.println(dump);
    }
    
}

