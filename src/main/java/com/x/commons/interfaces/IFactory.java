package com.x.commons.interfaces;

/**
 * 抽象工厂
 *
 * @Date 2018-12-19 20:43
 * @Author AD
 */
@FunctionalInterface
public interface IFactory<T> {
    
    /**
     * 抽象工厂
     *
     * @return
     *
     * @throws Exception
     */
    T get() throws Exception;
    
}
