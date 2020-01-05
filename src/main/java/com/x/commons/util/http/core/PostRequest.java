package com.x.commons.util.http.core;

import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.enums.HttpContentType;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/**
 * @Desc
 * @Date 2020-01-05 15:11
 * @Author AD
 */
public class PostRequest extends BaseHttpRequest {
    
    private final String url;
    
    private final Json param;
    
    private final boolean isForm;
    
    public PostRequest(String url, Json param) {
        this(url, param, false);
    }
    
    public PostRequest(String url, Json param, boolean isForm) {
        this.url = url;
        this.param = param;
        this.isForm = isForm;
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setEntity(getEntity(config));
        return post;
    }
    
    private HttpEntity getEntity(HttpConfig config) {
        String contentType = isForm ? HttpContentType.FORM : HttpContentType.JSON;
        ContentType type = ContentType.create(contentType, config.getInEncoding());
        String reqParam = param == null ? "" : (isForm ? param.toKeyValue() : param.toJson());
        return new StringEntity(reqParam, type);
    }
    
}
