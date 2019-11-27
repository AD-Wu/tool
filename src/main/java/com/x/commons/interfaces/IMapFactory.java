package com.x.commons.interfaces;

/**
 * @Desc 映射工厂
 * @Date 2019-10-24 21:50
 * @Author AD
 */
public interface IMapFactory<T, E> {
    
    /**
     * 对输入进行改造
     *
     * @param e
     *
     * @return
     *
     * @throws Exception
     */
    T get(E e) throws Exception;
    
}
