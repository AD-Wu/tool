package com.x.commons.util.reflact;

import com.x.commons.util.collection.XArrays;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Date 2019-03-04 20:52
 * @Author AD
 */
public final class Proxys {

    public static <T, R> R getProxy(T target, Class<R> targetInterface) {

        final Class<?>[] interfaces = target.getClass().getInterfaces();

        if (XArrays.isEmpty(interfaces)) {
            throw new RuntimeException("The target class need to implements interface");
        }

        return (R) Proxy.newProxyInstance(Loader.get(), interfaces, new InvocationHandler() {
            @Override
            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                System.out.println("before invoke");
                return method.invoke(target, args);
            }
        });
    }
}
