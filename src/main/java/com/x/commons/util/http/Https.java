package com.x.commons.util.http;

import com.x.commons.util.http.core.*;
import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.data.HttpResult;
import com.x.commons.util.http.enums.HttpEntityMethod;
import com.x.commons.util.http.factory.HttpConfig;

/**
 * @Desc http工具类
 * @Date 2019-11-16 19:42
 * @Author AD
 */
public final class Https {
    
    // ------------------------ 变量定义 ------------------------
    
    // ------------------------ 构造方法 ------------------------
    
    private Https() {}
    
    // ------------------------ 方法定义 ------------------------
    
    public static HttpResult get(String url) throws Exception {
        return get(url, HttpConfig.defaultConfig(url));
    }
    
    public static HttpResult get(String url, HttpConfig config) throws Exception {
        return new GetRequest(url).send(config);
    }
    
    public static HttpResult post(String url, HttpParam param) throws Exception {
        return post(url, param, HttpConfig.defaultConfig(url));
    }
    
    public static HttpResult post(String url, HttpParam param, HttpConfig config) throws Exception {
        return new PostRequest(url, param).send(config);
    }
    
    public static HttpResult put(String url, HttpParam param) throws Exception {
        return put(url, param, HttpConfig.defaultConfig(url));
    }
    
    public static HttpResult put(String url, HttpParam param, HttpConfig config) throws Exception {
        return new PutRequest(url, param).send(config);
    }
    
    public static HttpResult patch(String url, HttpParam param) throws Exception {
        return patch(url, param, HttpConfig.defaultConfig(url));
    }
    
    public static HttpResult patch(String url, HttpParam param, HttpConfig config) throws Exception {
        return new PatchRequest(url, param).send(config);
    }
    
    public static HttpResult delete(String url) throws Exception {
        return delete(url, HttpConfig.defaultConfig(url));
    }
    
    public static HttpResult delete(String url, HttpConfig config) throws Exception {
        return new DeleteRequest(url).send(config);
    }
    
    public static HttpResult head(String url) throws Exception {
        return head(url, HttpConfig.defaultConfig(url));
    }
    
    public static HttpResult head(String url, HttpConfig config) throws Exception {
        return new HeadRequest(url).send(config);
    }
    
    public static HttpResult trace(String url) throws Exception {
        return trace(url, HttpConfig.defaultConfig(url));
    }
    
    public static HttpResult trace(String url, HttpConfig config) throws Exception {
        return new TraceRequest(url).send(config);
    }
    
    public static HttpResult options(String url) throws Exception {
        return options(url, HttpConfig.defaultConfig(url));
    }
    
    public static HttpResult options(String url, HttpConfig config) throws Exception {
        return new OptionsRequest(url).send(config);
    }
    
    public static HttpResult upload(String url, HttpParam param) throws Exception {
        return upload(url, param, HttpEntityMethod.POST);
    }
    
    public static HttpResult upload(String url, HttpParam param, HttpEntityMethod method) throws Exception {
        return upload(url, param, method, HttpConfig.formDataConfig(url));
    }
    
    public static HttpResult upload(String url, HttpParam param, HttpEntityMethod method, HttpConfig config) throws Exception {
        return new FileUploadRequest(url, param, method).send(config);
    }
    
}
