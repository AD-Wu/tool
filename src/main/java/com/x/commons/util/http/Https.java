package com.x.commons.util.http;

import com.x.commons.util.http.core.DeleteRequest;
import com.x.commons.util.http.core.GetRequest;
import com.x.commons.util.http.core.PostRequest;
import com.x.commons.util.http.core.PutRequest;
import com.x.commons.util.http.data.HttpResult;
import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.factory.HttpConfig;
import com.x.commons.util.string.Strings;

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
    
    public static HttpResult get(String url, Json param) throws Exception {
        HttpConfig config = HttpConfig.defaultConfig(isHttps(url));
        return get(url, param, config);
    }
    
    public static HttpResult get(String url, Json param, HttpConfig config) throws Exception {
        return new GetRequest(url, param).send(config);
    }
    
    public static HttpResult post(String url, Json param) throws Exception {
        return post(url, param, false);
    }
    
    public static HttpResult post(String url, Json param, boolean isForm) throws Exception {
        HttpConfig config = HttpConfig.defaultConfig(isHttps(url));
        return post(url, param, isForm, config);
    }
    
    public static HttpResult post(String url, Json param, boolean isForm, HttpConfig config) throws Exception {
        return new PostRequest(url, param, isForm).send(config);
    }
    
    public static HttpResult put(String url, Json param) throws Exception {
        HttpConfig config = HttpConfig.defaultConfig(isHttps(url));
        return put(url, param, config);
    }
    
    public static HttpResult put(String url, Json param, HttpConfig config) throws Exception {
        return new PutRequest(url, param).send(config);
    }
    
    public static HttpResult delete(String url, Json param) throws Exception {
        HttpConfig config = HttpConfig.defaultConfig(isHttps(url));
        return delete(url, param, config);
    }
    
    public static HttpResult delete(String url, Json param, HttpConfig config) throws Exception {
        return new DeleteRequest(url, param).send(config);
    }
    
    // ------------------------ 私有方法 ------------------------
    
    private static boolean isHttps(String url) {
        if (Strings.isNull(url)) {
            return false;
        }
        if (url.toLowerCase().startsWith("https://")) {
            return true;
        }
        return false;
    }
    
}
