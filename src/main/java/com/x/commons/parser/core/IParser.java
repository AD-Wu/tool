package com.x.commons.parser.core;

/**
 * @Desc TODO
 * @Date 2019-11-23 14:47
 * @Author AD
 */
public interface IParser<R, S> {
    
    R parse(S s) throws Exception;
    
}
