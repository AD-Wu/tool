package com.x.commons.util.reflact;

import com.x.commons.util.bean.New;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Date 2019-01-01 23:10
 * @Author AD
 */
public final class Methods {
    
    private static final Method[] EMPTY = new Method[0];
    
    private Methods() {}
    
    public static Method[] getMethods(Class<?> target, Class<? extends Annotation>... annotations) {
        MethodUtils.getMethodsListWithAnnotation(null, null, false, false);
        MethodUtils.getMethodsListWithAnnotation(null, null);
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
    
    public static Method getMethod(Class<?> target, String methodName) {
        final Method[] ms = target.getDeclaredMethods();
        return Stream.of(ms).filter(m -> m.getName().equals(methodName)).findFirst().get();
    }
    
    public static String getCurrentMethodName() {
        /**
         * invokers[0].getMethodName = getStackTrace 当前线程压栈方法
         * invokers[1].getMethodName = getCurrentMethodName 本方法
         * invokers[2].getMethodName = 调用本方法的方法
         */
        final StackTraceElement[] invokers = Thread.currentThread().getStackTrace();
        return invokers[2].getMethodName();
    }
    
    public static void main(String[] args) {
        String currentMethodName = Methods.getCurrentMethodName();
        System.out.println(currentMethodName);
    }
    
}
