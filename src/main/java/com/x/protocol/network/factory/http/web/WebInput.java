package com.x.protocol.network.factory.http.web;

import com.x.commons.util.collection.XArrays;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkInput;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.utils.HttpRequestHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Desc TODO
 * @Date 2020-03-05 23:24
 * @Author AD
 */
public final class WebInput extends NetworkInput implements IWebInput {
    
    private final HttpServletRequest request;
    
    public WebInput(NetworkService service, NetworkConsent consent, HttpServletRequest request) {
        super(service, consent);
        this.request = request;
    }
    
    @Override
    public String getParameter(String key) {
        return request.getParameter(key);
    }
    
    @Override
    public HttpServletRequest getRequest() {
        return this.request;
    }
    
    @Override
    public byte[] getInputStreamData(int length) {
        try {
            return HttpRequestHelper.getContentData(request, length);
        } catch (IOException e) {
            e.printStackTrace();
            return XArrays.EMPTY_BYTE;
        }
    }
    
}
