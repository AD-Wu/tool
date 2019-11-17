package com.x.commons.util.test;

import com.x.commons.anno.AutoRun;
import lombok.NonNull;

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
        final Object o = clazz.newInstance();

        Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getAnnotation(AutoRun.class) != null && ("".equals(method) || method.equals(m.getName())))
                .forEach(m -> {

                    try {
                        m.setAccessible(true);
                        m.invoke(o, m.getParameters());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
    }

}
