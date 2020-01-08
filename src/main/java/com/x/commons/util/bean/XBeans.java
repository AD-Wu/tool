package com.x.commons.util.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Desc TODO
 * @Date 2019-10-23 21:56
 * @Author AD
 */
public class XBeans implements ApplicationContextAware {

    private static ApplicationContext context;

    protected XBeans() {}

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }
}
