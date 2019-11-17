package com.x.commons.interfaces;

/**
 * @Desc TODO
 * @Date 2019-10-24 21:50
 * @Author AD
 */
public interface IIFactory<T, E> {

    T get(E e) throws Exception;

}
