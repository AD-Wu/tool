package com.x.commons.util.http.core;

import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc Http put请求，put支持entity参数（post、patch也支持，其它不支持）
 * @Date 2020-01-05 22:54
 * @Author AD
 */
public class PutRequest extends BaseHttpParamRequest {
    
    public PutRequest(String url, HttpParam param) {
        super(url, param);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        HttpPut put = new HttpPut(url);
        put.setEntity(super.getEntity(config, param));
        return put;
    }
    
}
