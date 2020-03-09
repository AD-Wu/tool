package com.x.protocol.network.factory.http.web;

import com.x.protocol.network.interfaces.INetworkInput;

import javax.servlet.http.HttpServletRequest;

/**
 * @Desc TODO
 * @Date 2020-03-05 23:20
 * @Author AD
 */
public interface IWebInput extends INetworkInput {
    
    String getParameter(String key);
    
    HttpServletRequest getRequest();
    
    byte[] getInputStreamData(int length);
    
}
