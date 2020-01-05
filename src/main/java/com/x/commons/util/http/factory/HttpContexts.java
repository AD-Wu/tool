package com.x.commons.util.http.factory;

import com.x.commons.interfaces.IFactory;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;

/**
 * @Desc TODO
 * @Date 2020-01-05 14:15
 * @Author AD
 */
public class HttpContexts implements IFactory<HttpContext> {
    
    private static final HttpContexts SELF = new HttpContexts();
    
    public static HttpContext create() {
        try {
            return SELF.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public HttpContext get() throws Exception {
        return new HttpClientContext();
    }
    
}
