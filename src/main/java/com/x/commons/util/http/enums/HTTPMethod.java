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
        public HttpRequestBase getHttpRequest() {
            return new HttpGet();
        }
    },
    
    POST("POST") {
        @Override
        public HttpRequestBase getHttpRequest() {
            return new HttpPost();
        }
    },
    
    HEAD("HEAD") {
        @Override
        public HttpRequestBase getHttpRequest() {
            return new HttpHead();
        }
    },
    
    PUT("PUT") {
        @Override
        public HttpRequestBase getHttpRequest() {
            return new HttpPut();
        }
    },
    
    DELETE("DELETE") {
        @Override
        public HttpRequestBase getHttpRequest() {
            return new HttpDelete();
        }
    },
    
    TRACE("TRACE") {
        @Override
        public HttpRequestBase getHttpRequest() {
            return new HttpTrace();
        }
    },
    
    PATCH("PATCH") {
        @Override
        public HttpRequestBase getHttpRequest() {
            return new HttpPatch();
        }
    },
    
    OPTIONS("OPTIONS") {
        @Override
        public HttpRequestBase getHttpRequest() {
            return new HttpOptions();
        }
    };
    
    public String getMethod() {
        return this.method;
    }
    
    private final String method;
    
    private HTTPMethod(String method) {
        this.method = method;
    }
    
    public abstract HttpRequestBase getHttpRequest();
    
}
