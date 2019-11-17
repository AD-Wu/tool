package com.x.commons.util.reflact;

import com.x.commons.util.bean.New;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Date 2019-01-01 23:10
 * @Author AD
 */
public enum Methods {
    ;

    private static final Method[] EMPTY = new Method[0];

    public static Method[] getMethods(Class<?> target, Class<? extends Annotation>... annotations) {
        final Method[] ms = target.getDeclaredMethods();
        final List<Method> list = New.list(ms.length);
        for (Method m : ms) {
            for (Class a : annotations) {
                if (m.isAnnotationPresent(a)) {
                    list.add(m);
                }
            }
        }
        return list.toArray(EMPTY);
    }
}
