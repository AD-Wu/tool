package com.x.protocol.network.factory.http.comet;

import com.x.commons.enums.Charset;
import com.x.commons.util.bean.New;
import com.x.commons.util.http.enums.HttpHeaderKey;
import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkIO;
import com.x.protocol.network.factory.http.HttpService;
import com.x.protocol.network.factory.http.utils.HttpRequestHelper;
import com.x.protocol.network.factory.http.web.WebInput;
import com.x.protocol.network.factory.http.web.WebOutput;
import com.x.protocol.network.factory.http.web.WebServlet;
import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometProcessor;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.x.protocol.network.factory.http.enums.HttpKey.PARAM_S_COMET_TIMEOUT;
import static com.x.protocol.network.factory.http.enums.HttpKey.PARAM_S_CONTENT_ENCODING;

/**
 * @Desc
 * @Date 2020-03-08 12:31
 * @Author AD
 */
public class CometServlet extends HttpServlet implements CometProcessor {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private HttpService service;
    
    private String encoding;
    
    private String contentType;
    
    private long timeout;
    
    private Object lock = new Object();
    
    private Map<HttpServletResponse, CometConsent> respMap = New.concurrentMap();
    
    // ------------------------ 构造方法 ------------------------
    
    public CometServlet(HttpService service) {
        this.service = service;
        this.timeout = service.getConfig().getLong(PARAM_S_COMET_TIMEOUT, 120000L);
        this.encoding = service.getConfig().getString(PARAM_S_CONTENT_ENCODING, Charset.UTF8);
        this.contentType = "text/xml;charset=" + encoding;
    }
    
    // ------------------------ 方法定义 ------------------------
    @Override
    public void event(CometEvent event) throws IOException, ServletException {
        HttpServletRequest req = event.getHttpServletRequest();
        HttpServletResponse resp = event.getHttpServletResponse();
        if (event.getEventType() == CometEvent.EventType.BEGIN) {
            req.setAttribute("org.apache.tomcat.comet.timeout", timeout);
            resp.setCharacterEncoding(encoding);
            resp.setContentType(contentType);
            resp.setHeader(HttpHeaderKey.CONTENT_TYPE, contentType);
            CometParams params = new CometParams();
            params.setServlet(this);
            params.setResponse(resp);
            params.setSessionID(WebServlet.getSessionID(req));
            params.setRemotePort(req.getRemotePort());
            params.setRemoteHost(req.getRemoteHost());
            params.setLocalPort(req.getLocalPort());
            params.setLocalHost(req.getLocalAddr());
            params.setRemoteLocalHost(HttpRequestHelper.getRemoteLocalHost(req));
            CometConsent consent = new CometConsent("", service, NetworkConsentType.REMOTE_TO_LOCAL, params);
            if (consent.start()) {
                WebOutput out = new WebOutput(service, consent, resp, false);
                WebInput in = new WebInput(service, consent, req);
                NetworkIO io = new NetworkIO(service, consent, in, out);
                consent.notifyData(io);
                if (consent.isClosed()) {
                    WebServlet.sendRejectError(req, resp, consent);
                    event.close();
                } else {
                    synchronized (lock) {
                        if (consent.isClosed()) {
                            event.close();
                        } else {
                            respMap.put(resp, consent);
                        }
                    }
                }
            } else {
                event.close();
            }
        }
        // [BEGIN,READ,END,ERROR]
        else if (event.getEventType() != CometEvent.EventType.ERROR ||
                 event.getEventType() != CometEvent.EventType.END) {
            if (event.getEventType() == CometEvent.EventType.READ) {
                ServletInputStream in = req.getInputStream();
                byte[] buf = new byte[1024];
                while (in.available() > 0) {
                    int read = in.read(buf);
                    if (read < 0) {
                        closeConsent(resp, event);
                        break;
                    }
                }
            }
        } else {
            closeConsent(resp, event);
        }
    }
    
    @Override
    public void destroy() {
        super.destroy();
        CometConsent[] consents;
        synchronized (lock) {
            consents = respMap.values().toArray(new CometConsent[0]);
            respMap.clear();
        }
        for (CometConsent consent : consents) {
            consent.close();
        }
    }
    
    void endConsent(HttpServletResponse response) {
        if (response != null) {
            synchronized (lock) {
                respMap.remove(response);
            }
        }
    }
    
    // ------------------------ 私有方法 ------------------------
    
    private void closeConsent(HttpServletResponse resp,CometEvent event) throws IOException {
        CometConsent consent;
        synchronized (lock) {
            consent = respMap.remove(resp);
        }
        if (consent != null) {
            consent.close();
        }
        event.close();
    }
}
