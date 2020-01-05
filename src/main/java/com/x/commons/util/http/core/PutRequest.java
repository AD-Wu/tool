package com.x.commons.util.http.core;

import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc TODO
 * @Date 2020-01-05 22:54
 * @Author AD
 */
public class PutRequest extends BaseHttpRequest {
    
    public PutRequest(String url, Json param) {
        super(url, param);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        HttpPut put = new HttpPut(url);
        put.setEntity(getEntity(config, param));
        return put;
    }
    
}
