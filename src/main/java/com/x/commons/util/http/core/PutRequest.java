package com.x.commons.util.http.core;

import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.enums.HttpContentType;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/**
 * @Desc TODO
 * @Date 2020-01-05 22:54
 * @Author AD
 */
public class PutRequest extends BaseHttpRequest {
    
    private final String url;
    
    private final Json param;
    
    public PutRequest(String url, Json param) {
        this.url = url;
        this.param = param;
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        HttpPut put = new HttpPut(url);
        put.setEntity(getEntity(config));
        return put;
    }
    
    private HttpEntity getEntity(HttpConfig config) {
        ContentType type = ContentType.create(HttpContentType.JSON, config.getInEncoding());
        String reqParam = param == null ? "" : param.toJson();
        return new StringEntity(reqParam, type);
    }
    
}
