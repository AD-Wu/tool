package com.x.commons.util.http.core;

import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc TODO
 * @Date 2020-01-05 14:56
 * @Author AD
 */
public class GetRequest extends BaseHttpRequest {
    
    private final String url;
    
    private final Json param;
    
    private final String fixURL;
    
    public GetRequest(String url, Json param) {
        this.url = url;
        this.param = param;
        this.fixURL = fixURL(url, param);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        return new HttpGet(fixURL);
    }
    
}
