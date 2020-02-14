package com.x.commons.util.http.core;

import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc Http post请求，post支持entity参数（put、patch也支持，其它不支持）
 * @Date 2020-01-05 15:11
 * @Author AD
 */
public class PostRequest extends BaseHttpParamRequest {
    
    public PostRequest(String url, HttpParam param) {
        super(url, param);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setEntity(super.getEntity(config));
        return post;
    }
    
}
