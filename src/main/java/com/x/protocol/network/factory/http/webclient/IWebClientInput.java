package com.x.protocol.network.factory.http.webclient;

import com.x.protocol.network.interfaces.INetworkInput;
import org.apache.http.HttpResponse;

import java.io.File;

/**
 * @Desc TODO
 * @Date 2020-03-07 12:32
 * @Author AD
 */
public interface IWebClientInput extends INetworkInput, IWebClientHandler {
    
    boolean isCancelled();
    
    boolean isCompleted();
    
    boolean isSucceed();
    
    int getStatusCode();
    
    String getStatusMessage();
    
    String getFailedMessage();
    
    HttpResponse getResponse();
    
    byte[] getResponseStreamData();
    
    File getResponseFile();
    
    <T> T getRequestData();
    
}
