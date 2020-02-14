package com.x.commons.util.http.factory;

import com.x.commons.util.http.data.HttpParam;
import org.apache.http.HttpEntity;

/**
 * @Desc 自定义获取HttpEntity, 实现类需要实现该接口，并加上Google的@AutoService注解
 * @Date 2020-02-13 23:14
 * @Author AD
 */
public interface IHttpEntityGetter {
    
    /**
     * 获取HttpEntity
     *
     * @param param
     * @param config
     *
     * @return
     */
    HttpEntity getEntity(HttpParam param, HttpConfig config);
    
    /**
     * Http请求的Content-Type
     *
     * @return
     */
    String getContentType();
    
}
