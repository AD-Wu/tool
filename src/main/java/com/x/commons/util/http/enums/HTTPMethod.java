package com.x.commons.util.http.enums;

import org.apache.http.client.methods.*;

/**
 * @Desc http请求方法，如：POST，GET
 * @Date 2019-11-17 12:21
 * @Author AD
 */
public enum HTTPMethod {
    
    GET("GET") {
        @Override
        public HttpRequestBase getHttpRequest(String url) {
            return new HttpGet(url);
        }
    },
    
    POST("POST") {
        @Override
        public HttpRequestBase getHttpRequest(String url) {
            return new HttpPost(url);
        }
    },
    
    HEAD("HEAD") {
        @Override
        public HttpRequestBase getHttpRequest(String url) {
            return new HttpHead(url);
        }
    },
    
    PUT("PUT") {
        @Override
        public HttpRequestBase getHttpRequest(String url) {
            return new HttpPut(url);
        }
    },
    
    DELETE("DELETE") {
        @Override
        public HttpRequestBase getHttpRequest(String url) {
            return new HttpDelete(url);
        }
    },
    
    TRACE("TRACE") {
        @Override
        public HttpRequestBase getHttpRequest(String url) {
            return new HttpTrace(url);
        }
    },
    
    PATCH("PATCH") {
        @Override
        public HttpRequestBase getHttpRequest(String url) {
            return new HttpPatch(url);
        }
    },
    
    OPTIONS("OPTIONS") {
        @Override
        public HttpRequestBase getHttpRequest(String url) {
            return new HttpOptions(url);
        }
    };
    
    public String getMethod() {
        return this.method;
    }
    
    private final String method;
    
    private HTTPMethod(String method) {
        this.method = method;
    }
    
    public abstract HttpRequestBase getHttpRequest(String url);
    
}
