package com.x.protocol.network.factory.http.webclient;

import com.x.commons.collection.DataSet;
import com.x.commons.enums.Charset;
import com.x.commons.local.Locals;
import com.x.commons.util.string.Strings;
import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkIO;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.enums.HttpKey;
import com.x.protocol.network.factory.http.utils.HttpURLParser;
import com.x.protocol.network.interfaces.INetworkIO;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import static com.x.protocol.network.factory.http.enums.HttpKey.*;

/**
 * @Desc TODO
 * @Date 2020-03-07 13:50
 * @Author AD
 */
public class WebClientConsent extends NetworkConsent {
    
    String url;
    
    String downloadURL;
    
    String uploadURL;
    
    String method;
    
    String contentType;
    
    String charset;
    
    CloseableHttpAsyncClient httpClient;
    
    HttpClientContext localContext;
    
    private RequestConfig config;
    
    private Object clientLock = new Object();
    
    private CookieStore cookieStore;
    
    private String sessionID;
    
    /**
     * 网络应答对象构造方法
     *
     * @param name    网络应答对象名称
     * @param service 网络服务对象
     * @param type    网络应答对象类型
     * @param dataSet 数据集合
     */
    public WebClientConsent(String name, NetworkService service, NetworkConsentType type, DataSet dataSet) {
        super(name, service, type, true, false);
        this.url = dataSet.getString(PARAM_C_URL);
        this.downloadURL = dataSet.getString(PARAM_C_DOWNLOAD_URL);
        this.uploadURL = dataSet.getString(PARAM_C_UPLOAD_URL);
        this.method = dataSet.getString(PARAM_C_METHOD, "POST").toUpperCase();
        this.contentType = dataSet.getString("contentType", "text/html");
        this.charset = dataSet.getString(PARAM_C_CHARSET, Charset.UTF8);
        int socketTimeout = dataSet.getInt(PARAM_C_SOCKET_TIMEOUT, 30000);
        int connectTimeout = dataSet.getInt(PARAM_C_CONNECT_TIMEOUT, 30000);
        int requestTimeout = dataSet.getInt(PARAM_C_REQUEST_TIMEOUT, 60000);
        String proxyHost = dataSet.getString(PARAM_C_PROXY_HOST);
        int proxyPort = dataSet.getInt(PARAM_C_PROXY_PORT);
        RequestConfig.Builder config = RequestConfig.custom();
        config.setSocketTimeout(socketTimeout);
        config.setConnectTimeout(connectTimeout);
        config.setConnectionRequestTimeout(requestTimeout);
        if (!Strings.isNull(proxyHost) && proxyPort > 0) {
            config.setProxy(new HttpHost(proxyHost, proxyPort));
        }
        this.config = config.build();
        try {
            this.localHost = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            this.localHost = "127.0.0.1";
        }
        this.localPort = 0;
        HttpURLParser parser = HttpURLParser.parseUrl(url, charset);
        this.remoteHost = parser.getHost();
        this.remotePort = parser.getPort();
    }
    
    public INetworkIO getNetworkIO() {
        if (httpClient == null) {
            synchronized (clientLock) {
                if (httpClient == null) {
                    this.cookieStore = new BasicCookieStore();
                    this.localContext = HttpClientContext.create();
                    this.localContext.setCookieStore(cookieStore);
                    this.httpClient = HttpAsyncClients.custom().setDefaultRequestConfig(this.config).build();
                    this.httpClient.start();
                }
            }
        }
        WebClientOutput output = new WebClientOutput(service, this);
        return new NetworkIO(service, this, null, output);
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
            case HttpKey.INFO_C_TYPE:
                return "webclient";
            case HttpKey.INFO_C_SESSION_ID:
                if (sessionID == null && cookieStore != null) {
                    List<Cookie> cookies = cookieStore.getCookies();
                    for (Cookie cookie : cookies) {
                        String name = cookie.getName();
                        if (HttpKey.XWebID.equals(name) ||
                            HttpKey.JSessionID.equals(name)) {
                            this.sessionID = cookie.getValue();
                            break;
                        }
                    }
                }
                return this.sessionID;
            default:
                return super.service.getInformation(consentType);
        }
    }
    
    @Override
    protected void closeConsent() {
        synchronized (clientLock) {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    super.service.notifyError(this, Locals.text("protocol.http.close.err", e.getMessage()));
                }
                this.httpClient = null;
                this.cookieStore.clear();
                this.cookieStore = null;
                this.localContext = null;
            }
        }
    }
    
}
