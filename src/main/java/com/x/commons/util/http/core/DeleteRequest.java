package com.x.commons.util.http.core;

import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc TODO
 * @Date 2020-01-05 23:58
 * @Author AD
 */
public class DeleteRequest extends BaseHttpRequest {
    
    public DeleteRequest(String url) {
        super(url);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        return new HttpDelete(url);
    }
    
}
