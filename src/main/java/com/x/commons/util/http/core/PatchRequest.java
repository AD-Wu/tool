package com.x.commons.util.http.core;

import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc TODO
 * @Date 2020-01-06 00:14
 * @Author AD
 */
public class PatchRequest extends BaseHttpRequest {
    
    public PatchRequest(String url, Json param) {
        super(url, param);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        HttpPatch patch = new HttpPatch(url);
        patch.setEntity(getEntity(config, param));
        return patch;
    }
    
}
