package com.x.commons.util.test;

import com.x.commons.util.reflact.Clazzs;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author AD
 * @Date 2018/12/21 14:37
 */
public final class Auto {

    private Auto() {
    }

    public static void run(Class<?> clazz) throws Exception {
        run(clazz, "");
    }

    public static void run(Class<?> clazz, String method) throws Exception {
        final Object o = Clazzs.newInstance(clazz);
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getAnnotation(AutoRun.class) != null && ("".equals(
                        method) || method.equals(m.getName())))
                .forEach(m -> {
                    try {
                        invoke(m, o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
    }

    public static <T> T run(Class<?> clazz, String method, T result) throws Exception {
        final Object o = Clazzs.newInstance(clazz);
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getAnnotation(AutoRun.class) != null && ("".equals(
                        method) || method.equals(m.getName())))
                .map(m -> {
                    try {
                        Object invoke = invoke(m, o);
                        return (T) invoke;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }

                });
        return null;
    }

    private static Object invoke(Method m, Object o) throws Exception {
        m.setAccessible(true);
        Class<?>[] paramsType = m.getParameterTypes();
        Object[] params = new Object[paramsType.length];
        for (int i = 0; i < params.length; i++) {
            params[i] = Clazzs.newInstance(paramsType[i]);
        }
        Object invoke = m.invoke(o, params);
        return invoke;
    }
}
