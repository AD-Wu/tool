package com.x.protocol.network.factory.http.comet;

import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkIO;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.enums.HttpKey;
import com.x.protocol.network.factory.http.utils.HttpResponseHelper;
import com.x.protocol.network.factory.http.web.WebOutput;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkIO;

import javax.servlet.http.HttpServletResponse;

/**
 * @Desc
 * @Date 2020-03-08 12:36
 * @Author AD
 */
public final class CometConsent extends NetworkConsent implements INetworkConsent {
    
    private final CometParams params;
    
    /**
     * 网络应答对象构造方法
     *
     * @param name    网络应答对象名称
     * @param service 网络服务对象
     * @param type    网络应答对象类型
     * @param params  应答对象参数
     */
    public CometConsent(String name, NetworkService service, NetworkConsentType type, CometParams params) {
        super(name, service, type, true, false);
        this.params = params;
        super.remoteHost = params.getRemoteHost();
        super.remotePort = params.getRemotePort();
        super.localHost = params.getLocalHost();
        super.localPort = params.getLocalPort();
    }
    
    @Override
    public INetworkIO getNetworkIO() {
        WebOutput out = new WebOutput(super.service, this, params.getResponse(), true);
        return new NetworkIO(super.service, this, null, out);
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
                return "webcomet";
            case HttpKey.INFO_C_SESSION_ID:
                return this.params.getSessionID();
            default:
                return super.service.getInformation(consentType);
        }
    }
    
    @Override
    protected void closeConsent() {
        HttpServletResponse resp = params.getResponse();
        HttpResponseHelper.flush(resp);
        params.getServlet().endConsent(resp);
    }
    
}
