package com.x.protocol.network.factory.http;

import com.x.commons.collection.DataSet;
import com.x.commons.enums.Charset;
import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.file.Files;
import com.x.commons.util.string.Strings;
import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.comet.CometServlet;
import com.x.protocol.network.factory.http.enums.HttpKey;
import com.x.protocol.network.factory.http.web.WebServlet;
import com.x.protocol.network.factory.http.webclient.WebClientConsent;
import com.x.protocol.network.factory.http.websocketclient.WebSocketClientConsent;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkNotification;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11NioProtocol;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.InetAddress;
import java.net.URI;
import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-06 23:09
 * @Author AD
 */
public class HttpService extends NetworkService {
    private static final Object tomcatLock = new Object();

    private static final Map<String, Context> contextMap = New.concurrentMap();

    private static Tomcat tomcat;

    private static String webPath;

    private String apiPath;

    private String cometPath;

    private String wsPath;

    private String svcInfoText;

    public HttpService(INetworkNotification notification) {
        super(notification, true);
    }

    @Override
    protected Object getServiceInfo(String serviceProperty) {
        return super.config.get(serviceProperty);
    }

    @Override
    protected void serviceStart() {
        if (!super.isServerMode()) {
            super.notifyServiceStart();
        } else {
            boolean started = false;
            boolean first = false;
            synchronized (tomcatLock) {
                if (tomcat == null) {
                    started = startTomcat();
                    first = true;
                } else {
                    started = true;
                }
                if (started) {
                    addAPIContext();
                    addCometContext();
                    addWebSocketContext();
                }
            }
            if (started) {
                super.notifyServiceStart();
                if (first) {
                    tomcat.getServer().await();
                    super.stop();
                }
            } else {
                super.stop();
            }
        }
    }

    @Override
    protected void serviceStop() {
        if (super.isServerMode()) {
            synchronized (tomcatLock) {
                removeAPIContext();
                removeCometContext();
                removeWebSocketContext();
                if (contextMap.size() == 0) {
                    this.stopTomcat();
                }
            }
        }
    }

    @Override
    public String getServiceInfo() {
        if (this.svcInfoText == null) {
            String info = "HTTP";
            synchronized (tomcatLock) {
                if (tomcat == null) {
                    return info;
                }
                Connector connector = tomcat.getConnector();
                info = "HOST: \"" + connector.getScheme() + "://" + tomcat.getEngine().getDefaultHost() + ":" + connector.getPort() + "\"";
            }

            if (Strings.isNotNull(apiPath)) {
                info = info + "; API: \"" + apiPath + "\"";
            }

            if (Strings.isNotNull(cometPath)) {
                info = info + "; COMET: \"" + cometPath + "\"";
            }

            if (Strings.isNotNull(wsPath)) {
                info = info + "; WEBSOCKET: \"" + wsPath + "\"";
            }

            this.svcInfoText = info;
        }

        return this.svcInfoText;
    }

    @Override
    public INetworkConsent connect(String name, DataSet data) throws Exception {
        if (data != null && !super.isStopped()) {
            String client = data.getString(HttpKey.INFO_C_TYPE, "websocketclient").toLowerCase();
            WebSocketClientConsent webSocketClient = null;
            try {
                if ("websocketclient".equals(client)) {
                    String url = data.getString(HttpKey.PARAM_C_URL);
                    if (Strings.isNull(url)) {
                        return null;
                    }
                    webSocketClient = new WebSocketClientConsent(name, this,
                                                                 NetworkConsentType.LOCAL_TO_REMOTE,
                                                                 data);

                    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                    if (container.connectToServer(webSocketClient,
                                                  URI.create(
                                                          url)) != null && webSocketClient.start()) {
                        return webSocketClient;
                    }
                } else if ("webclient".equals(client)) {
                    WebClientConsent webConsent = new WebClientConsent(name, this,
                                                                       NetworkConsentType.LOCAL_TO_REMOTE,
                                                                       data);
                    if (webConsent.start()) {
                        return webConsent;
                    }
                }
                return null;
            } catch (Exception e) {
                if (webSocketClient != null) {
                    try {
                        webSocketClient.close();
                    } catch (Exception ex) {
                    }
                }
                throw new Exception(Locals.text("protocol.http.client.err", name, e.toString()));
            }
        } else {
            return null;
        }
    }


    private boolean startTomcat() {
        if (tomcat != null) return true;
        String host = config.getString(HttpKey.PARAM_S_HOST, "127.0.0.1");
        int port = config.getInt(HttpKey.PARAM_S_PORT, 80);
        String proxyHost = config.getString(HttpKey.PARAM_S_PROXY_HOST);
        int proxyPort = config.getInt(HttpKey.PARAM_S_PROXY_PORT);
        String tomcatPath = Files.getLocalPath(
                config.getString(HttpKey.PARAM_S_TOMCAT_PATH, "tomcat"), false);
        webPath = Files.getLocalPath(config.getString(HttpKey.PARAM_S_WEBSITE_PATH, "web"), false);
        String urlEncoding = config.getString(HttpKey.PARAM_S_URL_ENCODING);
        if (Strings.isNull(urlEncoding)) {
            urlEncoding = Charset.UTF8;
        }
        try {
            System.setProperty("catalina.home", tomcatPath);
            System.setProperty("catalina.base", tomcatPath);
            tomcat = new Tomcat();
            if (Strings.isNotEquals(host, "127.0.0.1", "localhost")) {
                tomcat.setHostname(host);
            } else {
                tomcat.setHostname(InetAddress.getLocalHost().getHostAddress());
            }
            tomcat.setPort(port);
            tomcat.setBaseDir(tomcatPath);
            Host tomcatHost = tomcat.getHost();
            tomcatHost.setAppBase(webPath);
            tomcat.addWebapp("/", webPath);
            tomcat.enableNaming();
            Connector connector = tomcat.getConnector();
            if (Strings.isNotNull(proxyHost) && proxyPort > 0) {
                connector.setProxyName(proxyHost);
                connector.setProxyPort(proxyPort);
            }
            connector.setProtocolHandlerClassName(Http11NioProtocol.class.getName());
            connector.setScheme("http");
            connector.setURIEncoding(urlEncoding);
            connector.setEnableLookups(false);
            tomcat.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            super.notifyError(null, Locals.text("protocol.http.start.err", e.getMessage()));
            return false;
        }

    }

    private void stopTomcat() {
        if (tomcat != null) {
            try {
                tomcat.stop();
            } catch (LifecycleException e) {
                e.printStackTrace();
                super.notifyError(null, Locals.text("protocol.http.stop.err", e.getMessage()));
            }
            tomcat = null;
        }
    }

    private void addAPIContext() {
        this.apiPath = config.getString(HttpKey.PARAM_S_API_URL);
        if (Strings.isNotNull(apiPath)) {
            if (!apiPath.startsWith("/")) {
                apiPath = "/" + apiPath;
            }
            if (!contextMap.containsKey(apiPath)) {
                String apiName = super.getName() + "-api";
                Context context = (StandardContext) tomcat.addContext(apiPath, webPath);
                Tomcat.addServlet(context, apiName, new WebServlet(this));
                context.addServletMapping("/*", apiName);
                contextMap.put(apiPath, context);
            }
        }
    }

    private void removeAPIContext() {
        if (Strings.isNotNull(apiPath)) {
            Context context = contextMap.remove(apiPath);
            if (context != null) {
                try {
                    context.stop();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addCometContext() {
        this.cometPath = config.getString(HttpKey.PARAM_S_COMET_URL);
        if (Strings.isNotNull(cometPath)) {
            if (!cometPath.startsWith("/")) {
                cometPath = "/" + cometPath;
            }
            if (!contextMap.containsKey(cometPath)) {
                String cometName = super.getName() + "-comet";
                Context context = (StandardContext) tomcat.addContext(cometPath, webPath);
                Tomcat.addServlet(context, cometName, new CometServlet(this));
                context.addServletMapping("/*", cometName);
                contextMap.put(cometPath, context);
            }
        }
    }

    private void removeCometContext() {
        if (Strings.isNotNull(cometPath)) {
            Context context = contextMap.remove(cometPath);
            if (context != null) {
                try {
                    context.stop();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addWebSocketContext() {
        this.wsPath = config.getString(HttpKey.PARAM_S_WEBSOCKET_URL);
        if (Strings.isNotNull(wsPath)) {
            if (!wsPath.startsWith("/")) {
                wsPath = "/" + wsPath;
            }
            if (!contextMap.containsKey(wsPath)) {
                String wsName = super.getName() + "-ws";
                Context context = (StandardContext) tomcat.addContext(wsPath, webPath);
                Tomcat.addServlet(context, wsName, new CometServlet(this));
                context.addServletMapping("/*", wsName);
                contextMap.put(wsPath, context);
            }
        }
    }

    private void removeWebSocketContext() {
        if (Strings.isNotNull(wsPath)) {
            Context context = contextMap.remove(wsPath);
            if (context != null) {
                try {
                    context.stop();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
