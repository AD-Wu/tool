package com.x.protocol.network.factory.http.websocketclient;

import com.x.commons.collection.DataSet;
import com.x.commons.enums.Charset;
import com.x.commons.local.Locals;
import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkIO;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.enums.HttpKey;
import com.x.protocol.network.factory.http.utils.HttpURLParser;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkIO;

import javax.websocket.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * @Desc TODO
 * @Date 2020-03-08 00:52
 * @Author AD
 */
@ClientEndpoint
public class WebSocketClientConsent extends NetworkConsent implements INetworkConsent {
    
    private Object reader;
    
    private WebSocketClientInput in;
    
    private WebSocketClientOutput out;
    
    private DataSet params;
    
    private Object lock = new Object();
    
    private String sessionID;
    
    /**
     * 网络应答对象构造方法
     *
     * @param name    网络应答对象名称
     * @param service 网络服务对象
     * @param type    网络应答对象类型
     * @param params  数据集合
     */
    public WebSocketClientConsent(String name, NetworkService service, NetworkConsentType type, DataSet params) {
        super(name, service, type, false, false);
        this.in = new WebSocketClientInput(service, this, null, null);
        this.params = params;
        String url = params.getString(HttpKey.PARAM_C_URL);
        String charset = params.getString(HttpKey.PARAM_C_CHARSET, Charset.UTF8);
        HttpURLParser parser = HttpURLParser.parseUrl(url, charset);
        super.remoteHost = parser.getHost();
        super.remotePort = parser.getPort();
        try {
            super.localHost = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            super.localHost = "127.0.0.1";
        }
        super.localPort = 0;
        
    }
    
    @OnOpen
    public void onOpen(Session session) {
        synchronized (lock) {
            sessionID = session.getId();
            out = new WebSocketClientOutput(service, this, session, params);
        }
    }
    
    @OnMessage
    public void onMessage(ByteBuffer buffer) {
        if (!super.isClosed()) {
            WebSocketClientInput input = new WebSocketClientInput(super.service, this, buffer, null);
            final NetworkIO io = new NetworkIO(super.service, this, input, out);
            if (!super.service.runSchedule(() -> {
                WebSocketClientConsent.this.notifyData(io);
            })) {
                notifyData(io);
            }
        }
    }
    
    @OnMessage
    public void onMessage(String msg) {
        if (!super.isClosed()) {
            WebSocketClientInput input = new WebSocketClientInput(super.service, this, null, msg);
            final NetworkIO io = new NetworkIO(super.service, this, input, out);
            if (!service.runSchedule(() -> {
                WebSocketClientConsent.this.notifyData(io);
            })) {
                notifyData(io);
            }
        }
    }
    
    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
        super.service.notifyError(this, Locals.text("protocol.wsc.err", error.getMessage()));
    }
    
    @OnClose
    public void onClose() {
        super.close();
    }
    
    @Override
    protected boolean checkDataAvailable() {
        return false;
    }
    
    @Override
    protected Object getConsentInfo(String consentType) {
        switch (consentType) {
            case HttpKey.INFO_C_REMOTE_HOST:
                return super.remoteHost;
            case HttpKey.INFO_C_REMOTE_PORT:
                return super.remotePort;
            case HttpKey.INFO_C_LOCAL_HOST:
                return super.localHost;
            case HttpKey.INFO_C_LOCAL_PORT:
                return super.localPort;
            case HttpKey.INFO_C_TYPE:
                return "websocketclient";
            case HttpKey.INFO_C_SESSION_ID:
                return this.sessionID;
            default:
                return super.service.getInformation(consentType);
        }
    }
    
    @Override
    protected void closeConsent() {
        if (out != null) {
            out.close();
        }
    }
    
    public INetworkIO getNetworkIO() {
        return new NetworkIO(super.service, this, in, out);
    }
    
    Object getReader() {
        return this.reader;
    }
    
    void setReader(Object reader) {
        this.reader = reader;
    }
    
}
