package com.x.protocol.network.factory.http.web;

import com.x.commons.util.string.Strings;

/**
 * @Desc
 * @Date 2020-03-05 23:49
 * @Author AD
 */
public class WebParams {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * http servlet
     */
    private WebServlet servlet;
    
    /**
     * 远端host
     */
    private String remoteHost;
    
    /**
     * 远端端口
     */
    private int remotePort;
    
    /**
     * 本地host
     */
    private String localHost;
    
    /**
     * 本地port
     */
    private int localPort;
    
    /**
     * 远端本地host
     */
    private String remoteLocalHost;
    
    /**
     * session ID
     */
    private String sessionID;
    
    // ------------------------ 构造方法 ------------------------
    
    /**
     * web参数构造方法
     */
    public WebParams() {}
    
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 获取http servlet
     *
     * @return WebServlet http servlet
     */
    public WebServlet getServlet() {
        return this.servlet;
    }
    
    /**
     * 设置http servlet
     *
     * @param servlet http servlet
     */
    public void setServlet(WebServlet servlet) {
        this.servlet = servlet;
    }
    
    /**
     * 获取远端host
     *
     * @return String 远端host
     */
    public String getRemoteHost() {
        return this.remoteHost;
    }
    
    /**
     * 设置远端host
     *
     * @param remoteHost 远端host
     */
    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }
    
    /**
     * 获取远端端口
     *
     * @return int 远端端口
     */
    public int getRemotePort() {
        return this.remotePort;
    }
    
    /**
     * 设置远端端口
     *
     * @param remotePort 远端端口
     */
    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }
    
    /**
     * 获取本地host
     *
     * @return String 本地host
     */
    public String getLocalHost() {
        return this.localHost;
    }
    
    /**
     * 设置本地host
     *
     * @param localHost 本地host
     */
    public void setLocalHost(String localHost) {
        this.localHost = localHost;
    }
    
    /**
     * 获取本地port
     *
     * @return int 本地port
     */
    public int getLocalPort() {
        return this.localPort;
    }
    
    /**
     * 设置本地port
     *
     * @param localPort 本地port
     */
    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }
    
    /**
     * 获取远端本地host
     *
     * @return String 远端本地host
     */
    public String getRemoteLocalHost() {
        return this.remoteLocalHost;
    }
    
    /**
     * 设置远端本地host
     *
     * @param remoteLocalHost 远端本地host
     */
    public void setRemoteLocalHost(String remoteLocalHost) {
        this.remoteLocalHost = remoteLocalHost;
    }
    
    /**
     * 获取session ID
     *
     * @return String session ID
     */
    public String getSessionID() {
        return this.sessionID;
    }
    
    /**
     * 设置session ID
     *
     * @param sessionID session ID
     */
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
