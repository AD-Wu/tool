package com.x.commons.convert.core;

/**
 * @Desc TODO
 * @Date 2020-03-13 01:14
 * @Author AD
 */
public abstract class BytesConverter<T> implements IConverter<T, byte[]> {
    
    @Override
    public T convert(byte[] bytes) throws Exception {
        return null;
    }
    
    @Override
    public byte[] reverse(T t) throws Exception {
        return new byte[0];
    }
    
    protected abstract T convertFrom(byte[] bytes) throws Exception;
    
    protected abstract byte[] reverseFrom(T t) throws Exception;
    
}
