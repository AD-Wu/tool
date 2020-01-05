package com.x.commons.util.http.enums;

import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.*;

/**
 * @Desc http请求方法，如：POST，GET
 * @Date 2019-11-17 12:21
 * @Author AD
 */
public enum HttpMethod {
    
    GET("GET") {
        @Override
        public HttpRequestBase getHttpRequest(String url, HttpConfig config) {
            HttpGet get = new HttpGet(url);
            GET.setConfig(get, config);
            return get;
        }
    },
    
    POST("POST") {
        @Override
        public HttpRequestBase getHttpRequest(String url, HttpConfig config) {
            HttpPost post = new HttpPost(url);
            POST.setConfig(post, config);
            return post;
        }
    },
    
    HEAD("HEAD") {
        @Override
        public HttpRequestBase getHttpRequest(String url, HttpConfig config) {
            HttpHead head = new HttpHead(url);
            HEAD.setConfig(head, config);
            return head;
        }
    },
    
    PUT("PUT") {
        @Override
        public HttpRequestBase getHttpRequest(String url, HttpConfig config) {
            HttpPut put = new HttpPut(url);
            PUT.setConfig(put, config);
            return put;
        }
    },
    
    DELETE("DELETE") {
        @Override
        public HttpRequestBase getHttpRequest(String url, HttpConfig config) {
            HttpDelete delete = new HttpDelete(url);
            DELETE.setConfig(delete, config);
            return delete;
        }
    },
    
    TRACE("TRACE") {
        @Override
        public HttpRequestBase getHttpRequest(String url, HttpConfig config) {
            HttpTrace trace = new HttpTrace(url);
            TRACE.setConfig(trace, config);
            return trace;
        }
    },
    
    PATCH("PATCH") {
        @Override
        public HttpRequestBase getHttpRequest(String url, HttpConfig config) {
            HttpPatch patch = new HttpPatch(url);
            PATCH.setConfig(patch, config);
            return patch;
        }
    },
    
    OPTIONS("OPTIONS") {
        @Override
        public HttpRequestBase getHttpRequest(String url, HttpConfig config) {
            HttpOptions options = new HttpOptions(url);
            OPTIONS.setConfig(options, config);
            return options;
        }
    };
    
    public String getMethod() {
        return this.method;
    }
    
    private final String method;
    
    private HttpMethod(String method) {
        this.method = method;
    }
    
    public abstract HttpRequestBase getHttpRequest(String url, HttpConfig config);
    
    private void setConfig(HttpRequestBase req, HttpConfig config) {
        req.setConfig(config.getRequestConfig());
        req.setHeaders(config.getHeaders());
    }
    
}
