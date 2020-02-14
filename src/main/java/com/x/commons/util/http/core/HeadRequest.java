package com.x.commons.util.http.core;

import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc TODO
 * @Date 2020-01-06 00:25
 * @Author AD
 */
public class HeadRequest extends BaseHttpRequest {
    
    public HeadRequest(String url) {
        super(url);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        return new HttpHead(url);
    }
    
}
