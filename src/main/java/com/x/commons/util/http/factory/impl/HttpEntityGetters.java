package com.x.commons.util.http.factory.impl;

import com.x.commons.util.http.factory.IHttpEntityGetter;
import com.x.commons.util.manager.Manager;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/5/13 17:43
 */
public final class HttpEntityGetters extends Manager<IHttpEntityGetter, String> {
    
    private static HttpEntityGetters self = new HttpEntityGetters(IHttpEntityGetter.class);
    
    private HttpEntityGetters(Class<IHttpEntityGetter> clazz) {
        super(clazz);
    }
    
    public static IHttpEntityGetter get(String contentType) {
        return self.getWorker(contentType);
    }
    
    @Override
    protected String[] getKeys(IHttpEntityGetter getter) {
        return new String[]{getter.getContentType()};
    }
    
}
