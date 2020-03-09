package com.x.protocol.network.factory.http.webclient;

import com.x.commons.collection.DataSet;
import com.x.protocol.network.interfaces.INetworkOutput;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;

import java.io.File;

/**
 * @Desc TODO
 * @Date 2020-03-07 12:35
 * @Author AD
 */
public interface IWebClientOutput extends INetworkOutput {
    
    String getDefaultURL();
    
    String getDefaultDownloadURL();
    
    String getDefaultUploadURL();
    
    String getDefaultMethod();
    
    String getDefaultContentType();
    
    String getDefaultCharset();
    
    IWebClientHandler sendRequest(DataSet dataSet);
    
    IWebClientHandler sendRequest(HttpEntity entity);
    
    IWebClientHandler sendRequest(String url, byte[] bytes);
    
    IWebClientHandler sendRequest(String url, byte[] bytes, ContentType contentType);
    
    IWebClientHandler sendRequest(String url, HttpEntity entity);
    
    IWebClientHandler sendRequest(String url, String method, String contentType, DataSet dataSet);
    
    <T> void setRequestData(T t);
    
    IWebClientHandler uploadFile(File file);
    
    IWebClientHandler uploadFile(String url, File file);
    
    WebClientInput downloadFile(String savePath);
    
    WebClientInput downloadFile(String url, String savePath);
    
}
