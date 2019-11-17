package com.x.commons.util.reflact;

import com.x.commons.util.array.Arrays;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Date 2019-03-04 20:52
 * @Author AD
 */
public enum Proxys {
    ;

    private static ClassLoader loader = Loader.get();

    public static <T, R> R getProxy(T target, Class<R> targetInterface) {

        final Class<?>[] interfaces = target.getClass().getInterfaces();

        if (Arrays.isEmpty(interfaces)) {
            throw new RuntimeException("The target class need to implements interface");
        }

        return (R) Proxy.newProxyInstance(loader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                System.out.println("before invoke");
                return method.invoke(target, args);
            }
        });
    }
}
