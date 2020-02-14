package com.x.commons.util.http.core;

import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @Desc Http patch，patch支持entity参数（post、put也支持，其它不支持）
 * @Date 2020-01-06 00:14
 * @Author AD
 */
public class PatchRequest extends BaseHttpParamRequest {
    
    public PatchRequest(String url, HttpParam param) {
        super(url, param);
    }
    
    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        HttpPatch patch = new HttpPatch(url);
        patch.setEntity(super.getEntity(config, param));
        return patch;
    }
    
}
