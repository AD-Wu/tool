package com.x.protocol.network.factory.http.websocket;

import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkIO;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.enums.HttpKey;
import com.x.protocol.network.interfaces.INetworkIO;
import org.apache.catalina.websocket.WsOutbound;

/**
 * @Desc
 * @Date 2020-03-07 20:56
 * @Author AD
 */
public class WebSocketConsent extends NetworkConsent {
    
    /**
     * webSocket参数对象
     */
    private WebSocketParams params;
    
    /**
     * webSocket输入流
     */
    private WebSocketInput in;
    
    /**
     * webSocket输出流
     */
    private WebSocketOutput out;
    
    /**
     * 协议读取对象
     */
    private Object reader;
    
    /**
     * 网络应答对象构造方法
     *
     * @param name    网络应答对象名称
     * @param service 网络服务对象
     * @param type    网络应答对象类型
     */
    public WebSocketConsent(String name, NetworkService service, NetworkConsentType type, WebSocketParams params,
            WsOutbound out) {
        super(name, service, type, false, false);
        this.in = new WebSocketInput(service, this, null, null);
        this.out = new WebSocketOutput(service, this, out);
        this.params = params;
        this.remoteHost = params.getRemoteHost();
        this.remotePort = params.getRemotePort();
        this.localHost = params.getLocalHost();
        this.localPort = params.getLocalPort();
    }
    
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
                return "websocket";
            case HttpKey.INFO_C_SESSION_ID:
                return this.params.getSessionID();
            default:
                return super.service.getInformation(consentType);
        }
    }
    
    @Override
    protected void closeConsent() {
        if (out != null) {
            out.close();
            out = null;
        }
    }
    
    @Override
    public INetworkIO getNetworkIO(){
        return new NetworkIO(super.service, this, in, out);
    }
    
    /**
     * 获取webSocket输出流
     * @return WebSocketOutput webSocket输出流
     */
     WebSocketOutput getOutput() {
        return this.out;
    }
    
    /**
     * 获取协议读取对象
     * @return Object 协议读取对象
     */
     Object getReader() {
        return this.reader;
    }
    
    /**
     * 设置协议读取对象
     * @param reader 协议读取对象
     */
     void setReader(Object reader) {
        this.reader = reader;
    }
    
}
