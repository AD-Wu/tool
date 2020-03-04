package com.x.protocol.network.factory.http;

import com.x.commons.util.http.enums.HttpHeaderKey;
import com.x.commons.util.string.Strings;
import org.apache.http.message.AbstractHttpMessage;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc TODO
 * @Date 2020-03-03 00:35
 * @Author AD
 */
public final class HttpRequestHelper {
    
    // ------------------------ 变量定义 ------------------------
    
    private static final String[] MOBILE_KEYWORDS = new String[]{
            "mobile", "android", "symbianos", "iphone", "wp\\d*", "windows phone", "mqqbrowser", "nokia", "samsung", "midp-2",
            "untrusted/1.0", "windows ce", "blackberry", "ucweb", "brew", "j2me", "yulong", "coolpad", "tianyu", "ty-", "k-touch",
            "haier", "dopod", "lenovo", "huaqin", "aigo-", "ctc/1.0", "ctc/2.0", "cmcc", "daxian", "mot-", "sonyericsson",
            "gionee", "htc", "zte", "huawei", "webos", "gobrowser", "iemobile", "wap2.0", "WAPI"};
    
    // ------------------------ 构造方法 ------------------------
    
    private HttpRequestHelper() {}
    
    // ------------------------ 方法定义 ------------------------
    
    public static void setHttpClientHeader(AbstractHttpMessage msg, String contentType, String charset, String referer) {
        msg.setHeader(HttpHeaderKey.CONTENT_TYPE, contentType + ";charset=" + charset);
        msg.setHeader(HttpHeaderKey.CHARSET, charset);
        if (!Strings.isNull(referer)) {
            msg.setHeader(HttpHeaderKey.REFERER, referer);
        }
    }
    
    public static boolean isMobileAccess(HttpServletRequest req) {
        String agent = req.getHeader(HttpHeaderKey.USER_AGENT);
        if (!Strings.isNull(agent)) {
            agent = agent.trim().toLowerCase();
            Pattern pattern = Pattern.compile("wp\\d*");
            Matcher matcher = pattern.matcher(agent);
            if (agent.indexOf("windows nt") == -1 && agent.indexOf("ubuntu") == -1 ||
                agent.indexOf("windows nt") > -1 && matcher.find()) {
                for (int i = 0, c = MOBILE_KEYWORDS.length; i < c; ++i) {
                    Pattern compile = Pattern.compile(MOBILE_KEYWORDS[i]);
                    Matcher mobile = compile.matcher(agent);
                    if (mobile.find() && agent.indexOf("ipad") == -1 && agent.indexOf("ipod") == -1 &&
                        agent.indexOf("macintosh") == -1) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
    
    /**
     * 获取服务器域名
     *
     * @param request
     *
     * @return
     */
    public static String getServerDomain(HttpServletRequest request) {
        return request.getServerName();
    }
    
    public static int getServerPort(HttpServletRequest request) {
        return request.getServerPort();
    }
    
    public static String getFullRequestURL(HttpServletRequest req) {
        return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getRequestURI();
    }
    
    public static String getRemoteLocalHost(HttpServletRequest req) {
        String proxy = req.getHeader("x-forwarded-for");
        if (Strings.isNull(proxy, true, "unknown")) {
            proxy = req.getHeader("Proxy-Client-IP");
        }
        if (Strings.isNull(proxy, true, "unknown")) {
            proxy = req.getHeader("WL-Proxy-Client-IP");
        }
        if (Strings.isNull(proxy, true, "unknown")) {
            proxy = req.getRemoteAddr();
            if ("127.0.0.1".equals(proxy)) {
                try {
                    InetAddress localHost = InetAddress.getLocalHost();
                    proxy = localHost.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        if (proxy != null && proxy.length() > 15 && proxy.indexOf(",") > 0) {
            proxy = proxy.substring(0, proxy.indexOf(","));
        }
        return proxy == null ? "" : proxy;
    }
    
    public static int getRemotePort(HttpServletRequest request) {
        return request.getRemotePort();
    }
    
    public static String getRefererURL(HttpServletRequest req) {
        Enumeration<String> headers = req.getHeaders(HttpHeaderKey.REFERER);
        while (headers.hasMoreElements()) {
            String referer = headers.nextElement();
            if (!Strings.isNull(referer)) {
                return referer;
            }
        }
        return "";
    }
    
    public static byte[] getContentData(HttpServletRequest req, int length) throws IOException {
        if (req == null) {
            return new byte[0];
        }
        ServletInputStream in = req.getInputStream();
        ByteArrayOutputStream out;
        int read;
        int counter = 0;
        for (out = new ByteArrayOutputStream();
                (read = in.read()) != -1 && counter < length; ++counter) {
            out.write(read);
        }
        out.flush();
        out.close();
        return out.toByteArray();
    }
    
}
