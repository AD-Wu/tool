package com.x.protocol.network.factory.http.web;

import com.x.commons.enums.Charset;
import com.x.commons.local.LocalString;
import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;
import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkIO;
import com.x.protocol.network.factory.http.HttpService;
import com.x.protocol.network.factory.http.utils.HttpRequestHelper;
import com.x.protocol.network.factory.http.utils.HttpResponseHelper;
import com.x.protocol.network.interfaces.INetworkConsent;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Desc TODO
 * @Date 2020-03-05 23:49
 * @Author AD
 */
public final class WebServlet extends HttpServlet {
    
    // ------------------------ 变量定义 ------------------------
    private static final long serialVersionUID = 1L;
    
    public static final String SESSION_KEY = "XWEBSID";
    
    private final HttpService service;
    
    private final String encoding;
    
    private final String contentType;
    
    private final Object sessionLock = new Object();
    
    private final Map<String, WebConsent> sessions = New.concurrentMap();
    
    private final boolean skipIPVerify;
    
    // ------------------------ 构造方法 ------------------------
    
    public WebServlet(HttpService service) {
        this.service = service;
        this.encoding = service.getConfig().getString("contentEncoding", Charset.UTF8);
        this.contentType = "text/xml;charset=" + encoding;
        this.skipIPVerify = service.getConfig().getBoolean("skipIPVerify", false);
    }
    
    // ------------------------ 方法定义 ------------------------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        sendRejectError(req, resp, null);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应字符编码
        resp.setCharacterEncoding(this.encoding);
        // 设置响应类型
        resp.setContentType(this.contentType);
        // 设置头部响应类型信息
        resp.setHeader("Content-Type", this.contentType);
        WebConsent consent = null;
        // 获取会话ID
        String sid = getSessionID(req);
        if (sid != null && sid.length() == 32) {
            // 判断是否已经存在该会话
            consent = sessions.get(sid);
        }
        // 不存在该会话，创建网络应答对象信息
        if (consent == null) {
            WebParams params = new WebParams();
            params.setServlet(this);
            // 设置通信双方的信息
            params.setLocalHost(req.getLocalAddr());
            params.setLocalPort(req.getLocalPort());
            params.setRemoteHost(req.getRemoteHost());
            params.setRemotePort(req.getRemotePort());
            params.setRemoteLocalHost(HttpRequestHelper.getRemoteLocalHost(req));
            // 创建会话ID
            sid = createSessionID(resp);
            // 设置响应的会话ID
            params.setSessionID(sid);
            // 创建网络应答对象
            consent = new WebConsent(null, service, NetworkConsentType.REMOTE_TO_LOCAL, params);
            // 判断当前会话是否已启动
            if (consent.start()) {
                // 通知数据
                notifyData(consent, req, resp);
                // 当前会话未关闭且已登录
                if (!consent.isClosed() && consent.isLogin()) {
                    // 管理会话
                    sessions.put(sid, consent);
                } else {
                    // 关闭会话
                    consent.close();
                }
            } else {
                // 会话未开启，发送拒绝信息
                sendRejectError(req, resp, consent);
            }
        } else {
            // 网络应答对象已存在，或许通信双方信息
            String remoteHost = req.getRemoteHost();
            if (!Strings.isNull(remoteHost)) {
                // 跳过IP验证
                if (skipIPVerify) {
                    // 通知接收数据
                    notifyData(consent, req, resp);
                } else {
                    // 获取通信双方信息
                    String remoteLocalHost = HttpRequestHelper.getRemoteLocalHost(req);
                    String consentRemoteHost = (String) consent.getConsentInfo("consentRemoteHost");
                    String consentRemoteLocalHost = (String) consent.getConsentInfo("consentRemoteLocalHost");
                    // 合法通信
                    if (remoteHost.equals(consentRemoteHost) && remoteLocalHost.equals(consentRemoteLocalHost)) {
                        // 通知数据
                        notifyData(consent, req, resp);
                    } else {
                        // 发送拒绝信息，关闭会话
                        sendRejectError(req, resp, consent);
                        consent.close();
                    }
                }
            } else {
                sendRejectError(req, resp, consent);
            }
        }
        // 返回响应信息
        HttpResponseHelper.flush(resp);
    }
    
    public static String getSessionID(HttpServletRequest request) {
        String sid = request.getParameter("sid");
        if (Strings.isNull(sid)) {
            Cookie[] cookies = request.getCookies();
            if (!XArrays.isEmpty(cookies)) {
                for (Cookie cookie : cookies) {
                    String cookieName = cookie.getName();
                    if (SESSION_KEY.equalsIgnoreCase(cookieName)) {
                        sid = cookie.getValue();
                        break;
                    }
                }
            }
        }
        return sid;
    }
    
    public static String createSessionID(HttpServletResponse response) {
        String uuid = Strings.UUID();
        Cookie cookie = new Cookie(SESSION_KEY, uuid);
        cookie.setPath("/");
        response.addCookie(cookie);
        return uuid;
    }
    
    void endSession(String sessionKey) {
        if (!Strings.isNull(sessionKey)) {
            sessions.remove(sessionKey);
        }
    }
    
    public static void sendRejectError(HttpServletRequest req, HttpServletResponse resp, INetworkConsent consent) {
        String langKey = consent == null ? null : consent.getLangKey();
        if (Strings.isNull(langKey)) {
            Cookie[] cookies = req.getCookies();
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("XLANGKEY".equalsIgnoreCase(name)) {
                    langKey = cookie.getValue();
                    break;
                }
            }
            if (Strings.isNull(langKey)) {
                langKey = Locals.getDefaultLangKey();
            }
        }
        LocalString local = Locals.getLocal(langKey);
        try {
            HttpResponseHelper.sendError(resp, HttpServletResponse.SC_NOT_ACCEPTABLE, local.text("protocol.http.rejects", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ------------------------ 私有方法 ------------------------
    
    private void notifyData(WebConsent consent, HttpServletRequest req, HttpServletResponse resp) {
        WebInput in = new WebInput(service, consent, req);
        WebOutput out = new WebOutput(service, consent, resp, false);
        NetworkIO io = new NetworkIO(service, consent, in, out);
        consent.notifyData(io);
    }
    
}
