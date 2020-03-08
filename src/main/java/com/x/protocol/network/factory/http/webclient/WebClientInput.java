package com.x.protocol.network.factory.http.webclient;

import com.x.commons.util.collection.XArrays;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkIO;
import com.x.protocol.network.factory.NetworkInput;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.utils.HttpResponseHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.concurrent.FutureCallback;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Future;

/**
 * @Desc TODO
 * @Date 2020-03-07 12:42
 * @Author AD
 */
public class WebClientInput extends NetworkInput implements IWebClientInput, FutureCallback<HttpResponse> {
    
    // ------------------------ 变量定义 ------------------------
    
    private final Object requestData;
    
    Future<HttpResponse> future;
    
    HttpResponse response;
    
    File responseFile;
    
    boolean cancelled = false;
    
    int statusCode = 0;
    
    String statusMessage;
    
    boolean completed = false;
    
    boolean succeed = false;
    
    String failMessage;
    
    // ------------------------ 构造方法 ------------------------
    
    public WebClientInput(NetworkService service, NetworkConsent consent, Object requestData) {
        super(service, consent);
        this.requestData = requestData;
    }
    // ------------------------ FutureCallback API ------------------------
    
    @Override
    public void completed(HttpResponse response) {
        this.completed = true;
        this.response = response;
        if (response != null) {
            StatusLine statusLine = response.getStatusLine();
            this.statusCode = statusLine.getStatusCode();
            this.statusMessage = statusLine.getReasonPhrase();
            if (statusCode == HttpServletResponse.SC_OK) {
                this.succeed = true;
            }
        }
        this.notifyData();
    }
    
    @Override
    public void failed(Exception e) {
        this.failMessage = e.getMessage();
        this.notifyData();
    }
    
    @Override
    public void cancelled() {
        this.cancelled = true;
        this.notifyData();
    }
    
    private void notifyData() {
        WebClientOutput out = new WebClientOutput(service, consent);
        out.setRequestData(this.requestData);
        NetworkIO io = new NetworkIO(service, consent, this, out);
        super.consent.notifyData(io);
    }
    // ------------------------ IWebClientHandler API ------------------------
    
    @Override
    public boolean cancel() {
        return future != null ? future.cancel(true) : false;
    }
    
    // ------------------------ IWebClientInput API ------------------------
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public boolean isCompleted() {
        return this.completed;
    }
    
    @Override
    public boolean isSucceed() {
        return this.succeed;
    }
    
    @Override
    public int getStatusCode() {
        return this.statusCode;
    }
    
    @Override
    public String getStatusMessage() {
        return this.statusMessage;
    }
    
    @Override
    public String getFailedMessage() {
        return this.failMessage == null ? this.statusMessage : this.failMessage;
    }
    
    @Override
    public HttpResponse getResponse() {
        return this.response;
    }
    
    @Override
    public File getResponseFile() {
        return this.responseFile;
    }
    
    @Override
    public <T> T getRequestData() {
        return (T) this.requestData;
    }
    
    @Override
    public byte[] getResponseStreamData() {
        if (response == null) {
            return XArrays.EMPTY_BYTE;
        } else {
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return XArrays.EMPTY_BYTE;
            } else {
                try {
                    return HttpResponseHelper.readResponse(entity.getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                    return XArrays.EMPTY_BYTE;
                }
            }
        }
    }
    
}
