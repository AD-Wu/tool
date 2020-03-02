package com.x.protocol.network.factory.http;

import com.x.commons.util.http.enums.HttpHeaderKey;
import com.x.commons.util.string.Strings;
import org.apache.http.message.AbstractHttpMessage;

import javax.servlet.http.HttpServletRequest;
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
        msg.setHeader(HttpHeaderKey.CONTENT_TYPE, contentType+";charset="+charset);
        msg.setHeader(HttpHeaderKey.CHARSET, charset);
        if(!Strings.isNull(referer)){
            msg.setHeader(HttpHeaderKey.REFERER, referer);
        }
    }
    
    public static boolean isMobileAccess(HttpServletRequest req){
        String agent = req.getHeader(HttpHeaderKey.USER_AGENT);
        if(!Strings.isNull(agent)){
            agent = agent.trim().toLowerCase();
            Pattern pattern = Pattern.compile("wp\\d*");
            Matcher matcher = pattern.matcher(agent);
            
        }
        return false;
    }
    // ------------------------ 私有方法 ------------------------
    
}
