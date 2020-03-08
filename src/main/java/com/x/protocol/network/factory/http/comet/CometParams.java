package com.x.protocol.network.factory.http.comet;

import javax.servlet.http.HttpServletResponse;

/**
 * @Desc TODO
 * @Date 2020-03-08 12:52
 * @Author AD
 */
public class CometParams {
    
    private CometServlet servlet;
    
    private HttpServletResponse response;
    
    private String remoteHost;
    
    private int remotePort;
    
    private String localHost;
    
    private int localPort;
    
    private String remoteLocalHost;
    
    private String sessionID;
    
    public CometServlet getServlet() {
        return servlet;
    }
    
    public void setServlet(CometServlet servlet) {
        this.servlet = servlet;
    }
    
    public HttpServletResponse getResponse() {
        return response;
    }
    
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    public String getRemoteHost() {
        return remoteHost;
    }
    
    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }
    
    public int getRemotePort() {
        return remotePort;
    }
    
    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }
    
    public String getLocalHost() {
        return localHost;
    }
    
    public void setLocalHost(String localHost) {
        this.localHost = localHost;
    }
    
    public int getLocalPort() {
        return localPort;
    }
    
    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }
    
    public String getRemoteLocalHost() {
        return remoteLocalHost;
    }
    
    public void setRemoteLocalHost(String remoteLocalHost) {
        this.remoteLocalHost = remoteLocalHost;
    }
    
    public String getSessionID() {
        return sessionID;
    }
    
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
    
}
