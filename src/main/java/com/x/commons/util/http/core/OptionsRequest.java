package com.x.commons.util.http.core;

import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc TODO
 * @Date 2020-01-06 00:30
 * @Author AD
 */
public class OptionsRequest extends BaseHttpRequest {
    
    public OptionsRequest(String url) {
        super(url);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        return new HttpOptions(url);
    }
    
}
