package com.x.commons.util.http.factory;

import com.x.commons.enums.Charset;
import com.x.commons.util.convert.Converts;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.protocol.HttpContext;

import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Date 2019-12-25 22:21
 * @Author AD
 */
public final class HttpConfig {
    
    // ------------------------ 变量定义 ------------------------
    
    // http请求客户端
    private final HttpClient client;
    
    // http请求头部信息
    private final Header[] headers;
    
    // 用于cookie操作
    private final HttpContext context;
    
    // 默认超时时间，单位：秒
    private final int defaultTimeout;
    
    // 设置RequestConfig
    private final RequestConfig requestConfig;
    
    // 输入编码
    private final String inEncoding;
    
    // 输出编码
    private final String outEncoding;
    
    // ------------------------ 构造方法 ------------------------
    
    public static HttpConfig defaultConfig(boolean isHttps) {
        return isHttps ?
                new HttpConfig(new Builder(HttpClients.https())) :
                new HttpConfig(new Builder(HttpClients.http()));
    }
    
    public HttpConfig(Builder builder) {
        this.client = builder.client;
        this.headers = builder.headers;
        this.context = builder.context;
        this.defaultTimeout = builder.defaultTimeout;
        this.requestConfig = builder.requestConfig;
        this.inEncoding = builder.inEncoding;
        this.outEncoding = builder.outEncoding;
    }
    
    public static class Builder {
        
        // http请求客户端
        private final HttpClient client;
        
        // 头部信息
        private Header[] headers;
        
        // 用于cookie操作
        private HttpContext context;
        
        // 默认超时时间，单位：秒
        private int defaultTimeout;
        
        // 设置RequestConfig
        private RequestConfig requestConfig;
        
        // 输入编码
        private String inEncoding;
        
        // 输出编码
        private String outEncoding;
        
        public Builder(HttpClient client) {
            this.client = client;
            this.headers = HeaderBuilder.defaultBuild();
            this.context = HttpContexts.create();
            this.defaultTimeout = 60;
            this.timeout(defaultTimeout);
            this.inEncoding = Charset.UTF8;
            this.outEncoding = Charset.UTF8;
        }
        
        public HttpConfig build() {
            return new HttpConfig(this);
        }
        
        public Builder headers(Header[] headers) {
            this.headers = headers;
            return this;
        }
        
        public Builder context(HttpContext context) {
            this.context = context;
            return this;
        }
        
        public Builder inEncoding(String inEncoding) {
            this.inEncoding = inEncoding;
            return this;
        }
        
        public Builder outEncoding(String outEncoding) {
            this.outEncoding = outEncoding;
            return this;
        }
        
        public Builder timeout(int timeout) {
            return timeout(timeout, true);
        }
        
        /**
         * 设置超时时间以及是否允许网页重定向（自动跳转 302）
         *
         * @param timeout        超时时间，单位:秒
         * @param redirectEnable 自动跳转
         */
        public Builder timeout(int timeout, boolean redirectEnable) {
            // 修正超时时间
            if (timeout <= 0) {
                timeout = this.defaultTimeout;
            }
            int reqTimeout = Converts.toInt(TimeUnit.SECONDS.toMillis(timeout));
            // 配置请求的超时设置
            RequestConfig config = RequestConfig.custom()
                    .setConnectionRequestTimeout(reqTimeout)
                    .setConnectTimeout(reqTimeout)
                    .setSocketTimeout(reqTimeout)
                    .setRedirectsEnabled(redirectEnable)
                    .build();
            return requestConfig(config);
        }
        
        /**
         * 设置代理、超时时间、允许网页重定向等
         *
         * @param requestConfig 超时时间，单位：秒
         */
        public Builder requestConfig(RequestConfig requestConfig) {
            this.requestConfig = requestConfig;
            return this;
        }
        
    }
    
    // ------------------------ 方法定义 ------------------------
    
    public HttpClient getClient() {return client;}
    
    public Header[] getHeaders() {
        return headers;
    }
    
    public HttpContext getContext() {
        return context;
    }
    
    public String getInEncoding() {
        return inEncoding;
    }
    
    public String getOutEncoding() {
        return outEncoding;
    }
    
    public RequestConfig getRequestConfig() {
        return requestConfig;
    }
    
}
