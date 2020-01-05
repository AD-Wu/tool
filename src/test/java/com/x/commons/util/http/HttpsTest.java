package com.x.commons.util.http;

import com.x.commons.enums.Charset;
import com.x.commons.util.http.data.HttpResult;
import com.x.commons.util.http.data.Json;
import org.junit.jupiter.api.Test;

/**
 * @Desc TODO
 * @Date 2020-01-05 12:53
 * @Author AD
 */
class HttpsTest {
    
    @Test
    void get() throws Exception {
        String url = "http://localhost:8080/get";
        Json json = new Json.Builder().of("user", "阳光").of("age", 12).build();
        HttpResult resp = Https.get(url, json);
        byte[] result = resp.getResult();
        String s = new String(result, Charset.UTF8);
        System.out.println(resp);
        System.out.println(s);
    }
  
    @Test
    void post() throws Exception {
        String url = "http://localhost:8080/post/json";
        Json json = new Json.Builder().of("user", "ad").of("pwd", "123").of("age", 12).build();
        HttpResult resp = Https.post(url, json);
        byte[] result = resp.getResult();
        String s = new String(result, Charset.UTF8);
        System.out.println(resp);
        System.out.println(s);
    }
    
    @Test
    void put() throws Exception{
        String url = "http://localhost:8080/put";
        Json json = new Json.Builder().of("user", "ad").of("pwd", "123").of("age", 12).build();
        HttpResult resp = Https.put(url, json);
        byte[] result = resp.getResult();
        String s = new String(result, Charset.UTF8);
        System.out.println(resp);
        System.out.println(s);
    }
    
    @Test
    void delete() throws Exception{
        String url = "http://localhost:8080/delete";
        Json json = new Json.Builder().of("user", "阳光").of("age", 12).build();
        HttpResult resp = Https.delete(url, json);
        byte[] result = resp.getResult();
        String s = new String(result, Charset.UTF8);
        System.out.println(resp);
        System.out.println(s);
    }
   
    
}