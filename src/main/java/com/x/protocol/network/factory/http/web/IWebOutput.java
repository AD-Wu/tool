package com.x.protocol.network.factory.http.web;

import com.x.protocol.network.interfaces.INetworkOutput;

import javax.servlet.http.HttpServletResponse;

/**
 * @Desc TODO
 * @Date 2020-03-05 23:22
 * @Author AD
 */
public interface IWebOutput extends INetworkOutput {
    
    HttpServletResponse getResponse();
    
    boolean sendResponse(byte[] data);
    
    boolean sendResponse(String msg);
    
}
