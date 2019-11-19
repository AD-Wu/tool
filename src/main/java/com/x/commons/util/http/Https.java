package com.x.commons.util.http;

import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.factory.HttpClientFactory;

/**
 * @Desc http工具类
 * @Date 2019-11-16 19:42
 * @Author AD
 */
public final class Https {

    private Https() {}

    public static void post(String url, Json json) {
        HttpClientFactory fact = new HttpClientFactory.Builder().retry(3).build();
    }

    public static <T> void post(String url, T param) {
    }

}
