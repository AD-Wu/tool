package com.x.commons.util.http.core;

import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc TODO
 * @Date 2020-01-05 23:58
 * @Author AD
 */
public class DeleteRequest extends BaseHttpRequest {
    
    private final String fixURL;
    
    public DeleteRequest(String url, Json param) {
        super(url, param);
        this.fixURL = fixURL(url, param);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        return new HttpDelete(fixURL);
    }
    
}
