package com.x.protocol.network.factory.http.websocket;

import com.x.commons.enums.Charset;
import com.x.commons.local.Locals;
import com.x.commons.util.convert.Converts;
import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkIO;
import com.x.protocol.network.factory.http.enums.HttpKey;
import com.x.protocol.network.factory.http.HttpService;
import com.x.protocol.network.factory.http.utils.HttpRequestHelper;
import com.x.protocol.network.factory.http.web.WebServlet;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @Desc TODO
 * @Date 2020-03-08 00:14
 * @Author AD
 */
public class WebSocketServlets extends WebSocketServlet {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private HttpService service;
    
    private String encoding;
    
    // ------------------------ 构造方法 ------------------------
    
    public WebSocketServlets(HttpService service) {
        this.service = service;
        this.encoding = service.getConfig().getString(HttpKey.PARAM_S_CONTENT_ENCODING, Charset.UTF8);
    }
    
    // ------------------------ 方法定义 ------------------------
    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
        WebSocketParams params = new WebSocketParams();
        params.setSessionID(WebServlet.getSessionID(request));
        params.setRemoteHost(request.getRemoteHost());
        params.setRemotePort(request.getRemotePort());
        params.setLocalHost(request.getLocalAddr());
        params.setLocalPort(request.getLocalPort());
        params.setRemoteLocalHost(HttpRequestHelper.getRemoteLocalHost(request));
        return new WebSocketServlets.WebSocketMessage(params);
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
    }
    
    @Override
    public void destroy() {
        super.destroy();
    }
    
    private final class WebSocketMessage extends MessageInbound {
        
        private WebSocketParams params;
        
        private WebSocketConsent consent;
        
        WebSocketMessage(WebSocketParams params) {
            this.params = params;
        }
        
        @Override
        protected void onOpen(WsOutbound outbound) {
            if (consent != null) {
                consent.close();
            }
            this.consent = new WebSocketConsent("", WebSocketServlets.this.service, NetworkConsentType.REMOTE_TO_LOCAL,
                    this.params, null);
            if (!this.consent.start()) {
                ByteBuffer data = Converts.toByteBuffer(Locals.text("protocol.ws.rejects"),
                        WebSocketServlets.this.encoding);
                try {
                    outbound.close(1000, data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        }
        
        @Override
        protected void onClose(int status) {
            try {
                if (consent != null) {
                    consent.close();
                    consent = null;
                }
            } catch (Exception e) {
            }
        }
        
        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            if (consent != null) {
                WebSocketInput input = new WebSocketInput(WebSocketServlets.this.service, consent, null, message);
                
                NetworkIO io = new NetworkIO(WebSocketServlets.this.service, consent, input, consent.getOutput());
                
                if (!WebSocketServlets.this.service.runSchedule(() -> {
                    consent.notifyData(io);
                })) {
                    consent.notifyData(io);
                }
            }
        }
        
        @Override
        protected void onTextMessage(CharBuffer message) throws IOException {
            if (consent != null) {
                WebSocketInput input = new WebSocketInput(WebSocketServlets.this.service, consent, message, null);
                
                NetworkIO io = new NetworkIO(WebSocketServlets.this.service, consent, input, consent.getOutput());
                
                if (!WebSocketServlets.this.service.runSchedule(() -> {
                    WebSocketMessage.this.consent.notifyData(io);
                })) {
                    consent.notifyData(io);
                }
            }
        }
        
    }
    
}
