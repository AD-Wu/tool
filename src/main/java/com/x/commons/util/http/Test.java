package com.x.commons.util.http;

import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.data.HttpResult;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/6 15:01
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String url="http://127.0.0.1:5050/pass";
        HttpParam param = new HttpParam();
        param.add("count",1000);
        HttpResult result = Https.post(url, param);
        byte[] data = result.getResult();
        String s = new String(data, "UTF-8");
        System.out.println(s.length());
        System.out.println(s);

        param.add("toString",true);
        HttpResult strResult = Https.post(url, param);
        byte[] strBytes = strResult.getResult();
        String ss = new String(data, "UTF-8");
        System.out.println(ss.length());
        System.out.println(ss);
    }
}
