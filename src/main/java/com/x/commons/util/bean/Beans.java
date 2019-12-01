package com.x.commons.util.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Desc TODO
 * @Date 2019-10-23 21:56
 * @Author AD
 */
@Component
public final class Beans implements ApplicationContextAware {

    private static ApplicationContext context;
    private Beans(){};

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
    
    public static Object getBean(String name){
        return context.getBean(name);
    }

    public static <T>T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }

    public static <T>T getBean(String name,Class<T> clazz){
        return context.getBean(name, clazz);
    }
}
