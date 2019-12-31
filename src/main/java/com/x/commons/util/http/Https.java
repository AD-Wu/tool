package com.x.commons.util.http;

import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.enums.HTTPMethod;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc http工具类
 * @Date 2019-11-16 19:42
 * @Author AD
 */
public final class Https {

    private Https() {}

    public static void get(String url){
        HttpRequestBase req = HTTPMethod.GET.getHttpRequest(url);

    }
    public static void post(String url, Json json) {
    }

    public static <T> void post(String url, T param) {
    }

}
