package com.x.commons.util.http.core;

import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.data.HttpResult;
import com.x.commons.util.http.enums.HttpContentType;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * @Desc TODO
 * @Date 2020-01-05 14:51
 * @Author AD
 */
public abstract class BaseHttpRequest implements IHttpRequest {
    // ------------------------ 变量定义 ------------------------
    
    protected final String url;
    
    // ------------------------ 构造方法 ------------------------
    
    protected BaseHttpRequest(String url) {
        this.url = url;
    }
    
    // ------------------------ 方法定义 ------------------------
    
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
    
    // ------------------------ 私有方法 ------------------------
    
    /**
     * 获取Content-Type是application/json的entity
     *
     * @param config
     * @param param
     *
     * @return
     */
    protected HttpEntity getEntity(HttpConfig config, HttpParam param) {
        ContentType type = ContentType.create(HttpContentType.JSON, config.getInEncoding());
        String reqParam = param == null ? "" : param.toJson();
        return new StringEntity(reqParam, type);
    }
    
    /**
     * 关闭响应资源
     *
     * @param resp
     */
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
