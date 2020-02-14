package com.x.commons.util.http.core;

import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc TODO
 * @Date 2020-01-05 14:56
 * @Author AD
 */
public class GetRequest extends BaseHttpRequest {
    
    public GetRequest(String url) {
        super(url);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        return new HttpGet(url);
    }
    
}
