package com.x.commons.interfaces;

/**
 * @Date 2018-12-19 20:43
 * @Author AD
 */
@FunctionalInterface
public interface IFactory<T> {

    T get() throws Exception;

}
