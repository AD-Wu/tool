package com.x.commons.interfaces;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/7 15:22
 */
public interface IListener<T> {
    void event(T t) throws Exception;
}
