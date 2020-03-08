package com.x.protocol.network.factory.http.web;

import com.x.commons.local.Locals;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkOutput;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.utils.HttpResponseHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Desc TODO
 * @Date 2020-03-05 23:40
 * @Author AD
 */
public final class WebOutput extends NetworkOutput implements IWebOutput {
    
    private final HttpServletResponse response;
    
    private final boolean flushEnabled;
    
    public WebOutput(NetworkService service, NetworkConsent concent, HttpServletResponse response, boolean flushEnabled) {
        super(service, concent);
        this.response = response;
        this.flushEnabled = flushEnabled;
    }
    
    @Override
    public HttpServletResponse getResponse() {
        return this.response;
    }
    
    @Override
    public boolean sendResponse(byte[] data) {
        try {
            HttpResponseHelper.write(response, data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            super.service.notifyError(super.consent, Locals.text("protocol.http.send.err", e.getMessage()));
            return false;
        }
    }
    
    @Override
    public boolean sendResponse(String msg) {
        try {
            HttpResponseHelper.write(response, msg);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            super.service.notifyError(super.consent, Locals.text("protocol.http.send.err", e.getMessage()));
        }
        return false;
    }
    
    @Override
    public void flush() {
        if (flushEnabled) {
            HttpResponseHelper.flush(response);
        }
    }
    
}
