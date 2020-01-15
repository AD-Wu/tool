package com.x.framework.caching.datas;

import com.x.commons.util.bean.New;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public enum ClassCode {

    $BYTE(1, byte.class, Byte.class),
    $SHORT(2, short.class, Short.class),
    $INT(3, int.class, Integer.class),
    $LONG(4, long.class, Long.class),
    $FLOAT(5, float.class, Float.class),
    $DOUBLE(6, double.class, Double.class),
    $BOOLEAN(7, boolean.class, Boolean.class),
    $CHAR(8, char.class, Character.class),
    $STRING(9, String.class),
    $DATE(10, Date.class),
    $LOCAL_DATE_TIME(11, LocalDateTime.class);

    // 不能使用$BYTE.getCode()方法获取
    public static final int BYTE = 1;

    public static final int SHORT = 2;

    public static final int INT = 3;

    public static final int LONG = 4;

    public static final int FLOAT = 5;

    public static final int DOUBLE = 6;

    public static final int BOOLEAN = 7;

    public static final int CHAR = 8;

    public static final int STRING = 9;

    public static final int DATE = 10;

    public static final int LOCAL_DATE_TIME = 11;

    private final int code;

    private final Class<?>[] clazz;

    private static Map<Class<?>, Integer> codeMap;

    private static final Object LOCK = new Object();

    private static volatile boolean inited = false;

    public static int getType(Class<?> clazz) {
        if (!inited) {
            synchronized (LOCK) {
                if (!inited) {
                    init();
                }
            }
        }
        return codeMap.get(clazz);
    }

    private static void init() {
        codeMap = New.concurrentMap();
        ClassCode[] values = values();
        Arrays.stream(values).forEach(value -> {
            int code = value.getCode();
            Class<?>[] clazzs = value.getClazz();
            for (Class<?> clazz : clazzs) {
                codeMap.put(clazz, code);
            }
        });
        inited = true;
    }

    private ClassCode(int code, Class<?>... clazz) {
        this.code = code;
        this.clazz = clazz;
    }

    public int getCode() {
        return code;
    }

    public Class<?>[] getClazz() {
        return clazz;
    }
}
