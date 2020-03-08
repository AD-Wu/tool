package com.x.protocol.network.factory.http.websocket;

/**
 * @Desc webSocket 参数
 * @Date 2020-03-07 20:59
 * @Author AD
 */
public class WebSocketParams {
    
    // ------------------------ 变量定义 ------------------------
    
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
     * 本地端口
     */
    private int localPort;
    
    /**
     * 远端本地端口
     */
    private String remoteLocalHost;
    
    /**
     * 会话ID
     */
    private String sessionID;
    
    // ------------------------ 构造方法 ------------------------
    public WebSocketParams() {}
    // ------------------------ 方法定义 ------------------------
    
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
     * 获取本地端口
     *
     * @return int 本地端口
     */
    public int getLocalPort() {
        return this.localPort;
    }
    
    /**
     * 设置本地端口
     *
     * @param localPort 本地端口
     */
    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }
    
    /**
     * 获取远端本地端口
     *
     * @return String 远端本地端口
     */
    public String getRemoteLocalHost() {
        return this.remoteLocalHost;
    }
    
    /**
     * 设置远端本地端口
     *
     * @param remoteLocalHost 远端本地端口
     */
    public void setRemoteLocalHost(String remoteLocalHost) {
        this.remoteLocalHost = remoteLocalHost;
    }
    
    /**
     * 获取会话ID
     *
     * @return String 会话ID
     */
    public String getSessionID() {
        return this.sessionID;
    }
    
    /**
     * 设置会话ID
     *
     * @param sessionID 会话ID
     */
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
    
}
