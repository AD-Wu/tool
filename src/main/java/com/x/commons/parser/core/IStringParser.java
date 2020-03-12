package com.x.commons.parser.core;

/**
 * @Desc TODO
 * @Date 2019-11-21 21:22
 * @Author AD
 */
public abstract class IStringParser<T, S> implements IParser<T, S> {
    
    @Override
    public T parse(S s) throws Exception {
        return parseFrom(String.valueOf(s));
    }
    
    public abstract T parseFrom(String s) throws Exception;
    
}
