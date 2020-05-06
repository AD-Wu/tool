package com.x.commons.util.proxy;

import com.sun.istack.internal.NotNull;
import com.x.commons.util.reflact.Clazzs;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/4/30 14:50
 */
public final class Proxys {
    
    private static final Set<String> METHODS;
    
    static {
        METHODS = Arrays.stream(Object.class.getDeclaredMethods())
                .map(m -> m.getName())
                .collect(Collectors.toSet());
    }
    
    /**
     * 获取cglib动态代理
     *
     * @param clazz 被代理类（必须有无参构造函数）
     * @param aop   切面
     */
    public static <T> T getProxy(@NotNull Class<T> clazz, IAop aop) {
        Enhancer enhancer = new Enhancer();
        
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MethodInterceptor() {
            
            /**
             * @param o 代理类对象,只能被methodProxy反射调用
             * @param method 被代理类方法对象(不要用此对象进行反射调用)
             * @param args 被代理类参数
             * @param methodProxy 代理类方法（不能调用invoke，会递归发生栈溢出。需调用invokeSuper）
             * @return
             * @throws Throwable
             */
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                if (METHODS.contains(method.getName())) {
                    T t = Clazzs.newInstance(clazz);
                    Method target = clazz.getMethod(method.getName());
                    return target.invoke(t, args);
                } else {
                    if (aop != null) {
                        aop.before();
                        Object result = methodProxy.invokeSuper(o, args);
                        aop.after();
                        return result;
                    } else {
                        return methodProxy.invokeSuper(o, args);
                    }
                }
            }
        });
        return (T) enhancer.create();
    }
    
}
