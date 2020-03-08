package com.x.protocol.network.factory.http.web;

import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.enums.HttpKey;

/**
 * @Desc TODO
 * @Date 2020-03-05 23:48
 * @Author AD
 */
public class WebConsent extends NetworkConsent {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * web参数
     */
    private final WebParams params;
    
    // ------------------------ 构造方法 ------------------------
    
    /**
     * web网络应答对象构造方法
     *
     * @param name    网络应答对象名称
     * @param service 网络服务对象
     * @param type    网络应答对象类型
     * @param params  web参数
     */
    WebConsent(String name, NetworkService service, NetworkConsentType type, WebParams params) {
        super(name, service, type, false, false);
        this.params = params;
        super.remoteHost = params.getRemoteHost();
        super.remotePort = params.getRemotePort();
        super.localHost = params.getLocalHost();
        super.localPort = params.getLocalPort();
        
    }
    
    // ------------------------ 方法定义 ------------------------
    
    @Override
    protected boolean checkDataAvailable() {
        return false;
    }
    
    @Override
    protected Object getConsentInfo(String consentType) {
        switch (consentType) {
            case HttpKey.INFO_C_REMOTE_HOST:
                return this.remoteHost;
            case HttpKey.INFO_C_REMOTE_PORT:
                return this.remotePort;
            case HttpKey.INFO_C_LOCAL_HOST:
                return this.localHost;
            case HttpKey.INFO_C_LOCAL_PORT:
                return this.localPort;
            case HttpKey.INFO_C_REMOTE_LOCAL_HOST:
                return this.params.getRemoteLocalHost();
            case HttpKey.INFO_C_TYPE:
                return "web";
            case HttpKey.INFO_C_SESSION_ID:
                return this.params.getSessionID();
            default:
                return super.service.getInformation(consentType);
        }
    }
    
    @Override
    protected void closeConsent() {
        this.params.getServlet().endSession(this.params.getSessionID());
    }
    
}
