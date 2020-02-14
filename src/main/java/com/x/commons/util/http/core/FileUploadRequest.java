package com.x.commons.util.http.core;

import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.enums.HttpEntityMethod;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/6 13:38
 */
public class FileUploadRequest extends BaseHttpParamRequest {
    
    private final HttpEntityMethod method;
    
    public FileUploadRequest(String url, HttpParam param, HttpEntityMethod method) {
        super(url, param);
        this.method = method;
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        switch (this.method) {
            case PUT:
                return new PutRequest(url, param).getRequest(config);
            case POST:
                return new PostRequest(url, param).getRequest(config);
            case PATCH:
                return new PatchRequest(url, param).getRequest(config);
            default:
                return new PostRequest(url, param).getRequest(config);
        }
    }
    
}
