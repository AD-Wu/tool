package com.x.framework.database.core;

import com.x.commons.util.bean.New;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;

/**
 * @Desc：java-sql类型映射
 * @Author：AD
 * @Date：2020/3/24 12:06
 */
enum TypeMapping {
    BOOL(Types.CHAR, boolean.class, Boolean.class),
    CHAR(Types.CHAR, char.class, Character.class),
    BYTE(Types.TINYINT, byte.class, Byte.class),
    SHORT(Types.SMALLINT, short.class, Short.class),
    INT(Types.INTEGER, int.class, Integer.class),
    LONG(Types.BIGINT, long.class, Long.class),
    FLOAT(Types.FLOAT, float.class, Float.class),
    DOUBLE(Types.DOUBLE, double.class, Double.class),

    BIG_DECIMAL(Types.NUMERIC, BigDecimal.class),
    BYTES(Types.BLOB, byte[].class),
    STRING(Types.VARCHAR, String.class),
    DATE(Types.TIMESTAMP, Date.class, LocalDateTime.class, LocalDate.class, LocalTime.class);

    private static final Map<Class<?>, Integer> TYPES = New.map();
    private static final Map<Class<?>, TypeMapping> MAPPERS = New.map();
    static {
        for (TypeMapping mapper : values()) {
            Class<?>[] clazzs = mapper.getClasses();
            for (Class<?> clazz : clazzs) {
                TYPES.put(clazz, mapper.getSqlType());
                MAPPERS.put(clazz,mapper);
            }
        }
    }

    public static int getSQLType(Class<?> javaClass) {
        if (TYPES.containsKey(javaClass)) {
            return TYPES.get(javaClass);
        }
        return Types.OTHER;
    }

    public static TypeMapping getMapper(Class<?> javaClass){
        return MAPPERS.get(javaClass);
    }

    private final int sqlType;

    private final Class<?>[] classes;

    TypeMapping(int sqlType, Class<?>... classes) {
        this.sqlType = sqlType;
        this.classes = classes;
    }

    public Class<?>[] getClasses() {
        return classes;
    }

    public int getSqlType() {
        return sqlType;
    }
}
