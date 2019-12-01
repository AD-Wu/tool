package com.x.commons.interfaces;

/**
 * @Desc TODO
 * @Date 2019-11-30 20:00
 * @Author AD
 */
public interface ICallback<T> {
    
    void onCallback(T t) throws Exception;
    
}
