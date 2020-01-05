package com.x.commons.util.http.core;

import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc TODO
 * @Date 2020-01-06 00:25
 * @Author AD
 */
public class HeadRequest extends BaseHttpRequest {
    
    private final String fixURL;
    
    public HeadRequest(String url, Json param) {
        super(url, param);
        this.fixURL = fixURL(url, param);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        return new HttpHead(fixURL);
    }
    
}
