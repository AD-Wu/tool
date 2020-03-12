package com.x.commons.convert.core;

/**
 * @Desc TODO
 * @Date 2020-03-13 00:27
 * @Author AD
 */
public interface IConverter<T, E> {
    
    T convert(E e);
    
}
