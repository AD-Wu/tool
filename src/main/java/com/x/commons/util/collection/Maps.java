package com.x.commons.util.collection;

import com.x.commons.util.bean.New;
import com.x.commons.util.string.Strings;

import java.util.Arrays;
import java.util.*;

/**
 * @Desc
 * @Date 2019-11-08 14:19
 * @Author AD
 */
public final class Maps {
    
    private Maps() {}
    
    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }
    
    public static Map<String, String> toMap(Properties... props) {
        Map<String, String> map = New.map();
        Arrays.stream(props).forEach(p -> {
            Iterator<Map.Entry<Object, Object>> it = p.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Object, Object> next = it.next();
                String key = String.valueOf(next.getKey());
                String value = String.valueOf(next.getValue());
                map.put(key, value);
            }
        });
        return map;
    }
    
    public static <K, V> void printMap(Map<K, V> map) {
        printMap(map, "super map");
    }
    
    private static <K, V> void printMap(Map<K, V> map, String title) {
        map.entrySet().forEach(e -> {
            Object key = e.getKey();
            Object value = e.getValue();
            if (key instanceof Map) {
                printMap((Map<Object, Object>) key, "key is map");
            }
            if (value instanceof Map) {
                printMap((Map<Object, Object>) value, "value is map");
            }
            print(title);
            System.out.println(Strings.replace("key={0},{1}", key, key.getClass()));
            System.out.println(Strings.replace("value={0},{1}\n", value, value.getClass()));
        });
    }
    
    private static void print(String title) {
        System.out.println("------------- " + title + " -------------");
    }

    
}
