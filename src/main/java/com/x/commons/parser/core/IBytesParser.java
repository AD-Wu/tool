package com.x.commons.parser.core;

/**
 * @Desc TODO
 * @Date 2020-03-13 00:42
 * @Author AD
 */
public abstract class IBytesParser<R> implements IParser<R, byte[]> {
    
    @Override
    public R parse(byte[] bytes) throws Exception {
        return parseFrom(bytes);
    }
    
    protected abstract R parseFrom(byte[] bytes) throws Exception;
    
}
