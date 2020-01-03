package com.x.commons.util.http;

import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.enums.HTTPMethod;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.net.URLEncoder;

/**
 * @Desc http工具类
 * @Date 2019-11-16 19:42
 * @Author AD
 */
public final class Https {


    private Https() {}

    public static void get(String url, Json param) throws Exception {
        get(url, param, new HttpConfig());
    }

    public static void get(String url, Json param, HttpConfig config) throws Exception {
        String fixURL = fixURL(url,param,config.getInEncoding());
        HttpGet req = (HttpGet) HTTPMethod.GET.getHttpRequest(fixURL);
        req.setConfig(config.getRequestConfig());
        req.setHeaders(config.getHeaders());

    }

    public static void post(String url, Json json) {
    }

    public static <T> void post(String url, T param) {
    }

    private static String fixURL(String url, Json param,String encoding) throws Exception {
        int end = url.indexOf("?");
        String fixURL = url;
        if (end > 0) {
            fixURL = fixURL.substring(0, end);
            fixURL = fixURL + "?" + param.toKeyValue();
        }
        return URLEncoder.encode(fixURL,encoding);
    }

    private static HttpClient getHttpClient(String url){
        return null;
    }
}
