package com.x.commons.util.reflact;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Generic:泛型
 *
 * @Date 2019-01-02 21:12
 * @Author AD
 */
public enum Generics {
    ;

    /**
     * @param field
     * @return java.lang.Class<?>
     * @author AD
     * @date 2019-01-02 21:23
     */
    public static Class<?> getGenericType(Field field) {
        field.setAccessible(true);
        if (field.getType() == List.class) {
            // generic:通用; parameterized:参数
            final Type genericType = field.getGenericType();
            if (genericType == null) return Object.class;
            // 获取泛型参数类型
            if (genericType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericType;
                Class<?> actualClazz = (Class<?>) pt.getActualTypeArguments()[0];
                return actualClazz;
            }
        }
        return Object.class;
    }
}
