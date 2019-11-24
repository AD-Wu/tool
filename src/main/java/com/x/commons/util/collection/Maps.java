package com.x.commons.util.collection;

import com.x.commons.util.bean.New;
import com.x.commons.util.date.DateTimes;
import com.x.commons.util.json.test.User;
import com.x.commons.util.string.Strings;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @Desc TODO
 * @Date 2019-11-08 14:19
 * @Author AD
 */
public final class Maps {
    
    private Maps() {}
    
    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
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
    
    public static void main(String[] args) {
        
        Map<Object, Object> subMap = New.map();
        subMap.put(1, "subMap-1");
        subMap.put(2, new Date());
        
        Map<Object, Object> subMap1 = New.map();
        subMap1.put("subMap-2", String.class);
        
        Map<Object, Object> keyMap = New.map();
        keyMap.put("keyMap-1", 1);
        
        Map<Object, Object> keyMap2 = New.map();
        keyMap2.put("keyMap-2", 2);
        keyMap2.put("keyMap-2", new User());
        keyMap2.put("Date", new Date());
        
        Map<Object, Object> superMap = New.linkedMap();
        superMap.put("superMap", subMap);
        superMap.put("superMap-1", subMap1);
        superMap.put(keyMap, true);
        superMap.put(keyMap2, new User());
        
        printMap(superMap, "super map");
        
    }
    
}
