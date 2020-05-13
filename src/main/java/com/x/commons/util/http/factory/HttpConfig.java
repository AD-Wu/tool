package com.x.commons.util.http.factory;

import com.x.commons.enums.Charset;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.http.enums.HttpContentType;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HttpContext;

import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Date 2019-12-25 22:21
 * @Author AD
 */
public final class HttpConfig {

    // ------------------------ 变量定义 ------------------------

    // http请求客户端
    private final HttpClient client;

    // http请求头部信息
    private Header[] headers = HeaderBuilder.defaultBuild();

    // 用于cookie操作
    private HttpContext context = HttpContexts.create();

    // 默认超时时间，单位：秒
    private int timeout = 60;

    // 设置RequestConfig
    private RequestConfig requestConfig = setTimeout(timeout).getRequestConfig();

    // 输入编码
    private String inEncoding = Charset.UTF8;

    // 输出编码
    private String outEncoding = Charset.UTF8;

    // 请求体编码方式
    private ContentType contentType = ContentType.create(HttpContentType.JSON, this.inEncoding);

    // ------------------------ 构造方法 ------------------------

    public HttpConfig(String url) {
        this.client = HttpClientFactory.getClient(url);
    }

    // ------------------------ 方法定义 ------------------------

    public static HttpConfig jsonConfig(String url) {
        return defaultConfig(url, HttpContentType.JSON);
    }

    public static HttpConfig formConfig(String url) {
        return defaultConfig(url, HttpContentType.FORM);
    }

    public static HttpConfig formDataConfig(String url) {
        return defaultConfig(url, HttpContentType.FORM_DATA);
    }

    public static HttpConfig defaultConfig(String url) {
        return defaultConfig(url, HttpContentType.JSON);
    }

    public static HttpConfig defaultConfig(String url, String contentType) {
        return new HttpConfig(url).setContentType(contentType);
    }

    // ------------------------ get and set ------------------------
    public HttpClient getClient() {
        return client;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public HttpConfig setHeaders(Header[] headers) {
        this.headers = headers;
        return this;
    }

    public HttpContext getContext() {
        return context;
    }

    public HttpConfig setContext(HttpContext context) {
        this.context = context;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public HttpConfig setTimeout(int timeout) {
        setTimeout(timeout, true);
        return this;
    }

    /**
     * 设置超时时间以及是否允许网页重定向（自动跳转 302）
     *
     * @param timeout        超时时间，单位:秒
     * @param redirectEnable 自动跳转
     */
    public HttpConfig setTimeout(int timeout, boolean redirectEnable) {
        // 修正超时时间
        if (timeout <= 0) {
            timeout = this.timeout;
        }
        this.timeout = timeout;
        int reqTimeout = Converts.toInt(TimeUnit.SECONDS.toMillis(timeout));
        // 配置请求的超时设置
        this.requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(reqTimeout)
                .setConnectTimeout(reqTimeout)
                .setSocketTimeout(reqTimeout)
                .setRedirectsEnabled(redirectEnable)
                .build();
        return this;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    /**
     * 设置代理、超时时间、允许网页重定向等
     *
     * @param requestConfig 超时时间，单位：秒
     */
    public HttpConfig setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
        return this;
    }

    public String getInEncoding() {
        return inEncoding;
    }

    public HttpConfig setInEncoding(String inEncoding) {
        this.inEncoding = inEncoding;
        return this;
    }

    public String getOutEncoding() {
        return outEncoding;
    }

    public HttpConfig setOutEncoding(String outEncoding) {
        this.outEncoding = outEncoding;
        return this;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public HttpConfig setContentType(String contentType) {
        this.contentType = ContentType.create(contentType, this.inEncoding);
        return this;
    }
}
