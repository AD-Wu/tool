package com.x.commons.util.http.core;

import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;

/**
 * @Desc TODO
 * @Date 2020-01-06 00:29
 * @Author AD
 */
public class TraceRequest extends BaseHttpRequest {
    
    private final String fixURL;
    
    public TraceRequest(String url, Json param) {
        super(url, param);
        this.fixURL = fixURL(url, param);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        return new HttpTrace(fixURL);
    }
    
}
