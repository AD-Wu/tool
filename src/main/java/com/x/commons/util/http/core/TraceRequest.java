package com.x.commons.util.http.core;

import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;

/**
 * @Desc TODO
 * @Date 2020-01-06 00:29
 * @Author AD
 */
public class TraceRequest extends BaseHttpRequest {
    
    public TraceRequest(String url) {
        super(url);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        return new HttpTrace(url);
    }
    
}
