package com.x.commons.util.test;

import com.x.commons.util.reflact.Clazzs;
import lombok.NonNull;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * @Author AD
 * @Date 2018/12/21 14:37
 */
public final class Auto {

    private Auto() {}

    public static void run(@NonNull Class<?> clazz) throws Exception {
        run(clazz, "");
    }

    public static void run(@NonNull Class<?> clazz, @NonNull String method) throws Exception {
        Constructor<?> c = clazz.getDeclaredConstructor();
        c.setAccessible(true);
        final Object o = c.newInstance();

        Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getAnnotation(AutoRun.class) != null && ("".equals(method) || method.equals(m.getName())))
                .forEach(m -> {
                    try {
                        m.setAccessible(true);
                        Class<?>[] paramsType = m.getParameterTypes();
                        Object[] params = new Object[paramsType.length];
                        for (int i = 0; i < params.length; i++) {
                            params[i]= Clazzs.newInstance(paramsType[i]);
                        }
                        m.invoke(o, params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
    }

}
