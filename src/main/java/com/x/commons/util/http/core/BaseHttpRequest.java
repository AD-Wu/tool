package com.x.commons.util.http.core;

import com.x.commons.util.http.data.HttpResult;
import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * @Desc TODO
 * @Date 2020-01-05 14:51
 * @Author AD
 */
public abstract class BaseHttpRequest implements IHttpRequest {
    
    @Override
    public final HttpResult send(HttpConfig config) throws Exception {
        HttpClient client = config.getClient();
        HttpContext context = config.getContext();
        HttpRequestBase req = getRequest(config);
        req.setConfig(config.getRequestConfig());
        req.setHeaders(config.getHeaders());
        HttpResponse resp = null;
        HttpResult result = null;
        try {
            resp = client.execute(req, context);
            result = new HttpResult(resp);
        } finally {
            close(resp);
        }
        return result;
    }
    
    protected abstract HttpRequestBase getRequest(HttpConfig config) throws Exception;
    
    protected String fixURL(String url, Json param) {
        int end = url.indexOf("?");
        String fixURL = url;
        if (param != null) {
            if (end > 0) {
                fixURL = fixURL.substring(0, end);
            }
            fixURL = fixURL + "?" + param.toKeyValue();
        }
        return fixURL;
    }
    
    private void close(HttpResponse resp) {
        try {
            if (resp == null) return;
            //如果CloseableHttpResponse 是resp的父类，则支持关闭
            if (CloseableHttpResponse.class.isAssignableFrom(resp.getClass())) {
                ((CloseableHttpResponse) resp).close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
