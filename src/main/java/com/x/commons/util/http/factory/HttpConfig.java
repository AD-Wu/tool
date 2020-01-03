package com.x.commons.util.http.factory;

import com.x.commons.enums.Charset;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.http.data.HeaderBuilder;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.protocol.HttpContext;

import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Date 2019-12-25 22:21
 * @Author AD
 */
public class HttpConfig {

    // ------------------------ 变量定义 ------------------------

    // http请求客户端
    private HttpClient client;

    private Header[] headers;

    // 用于cookie操作
    private HttpContext context;

    // 默认超时时间，单位：秒
    private int defaultTimeout;

    // 设置RequestConfig
    private RequestConfig requestConfig;

    // 是否返回响应头部信息
    private boolean isReturnRespHeaders;

    // 输入编码
    private String inEncoding;

    // 输出编码
    private String outEncoding;

    // ------------------------ 构造方法 ------------------------

    public HttpConfig() {
        this.client = new HttpClientFactory().http();
        this.headers = HeaderBuilder.defaultBuild();
        this.defaultTimeout = 30;
        this.isReturnRespHeaders = true;
        this.timeout(defaultTimeout, isReturnRespHeaders);
        this.inEncoding = Charset.UTF8;
        this.outEncoding = Charset.UTF8;
    }

    // ------------------------ 方法定义 ------------------------

    public HttpConfig client(HttpClient client) {
        this.client = client;
        return this;
    }

    /**
     * @param headers Header头信息
     * @return 返回当前对象
     */
    public HttpConfig headers(Header[] headers) {
        headers(headers, true);
        return this;
    }

    /**
     * Header头信息(是否返回response中的headers)
     *
     * @param headers             Header头信息
     * @param isReturnRespHeaders 是否返回response中的headers
     * @return 返回当前对象
     */
    public HttpConfig headers(Header[] headers, boolean isReturnRespHeaders) {
        this.headers = headers;
        this.isReturnRespHeaders = isReturnRespHeaders;
        return this;
    }

    /**
     * @param context cookie操作相关
     * @return 返回当前对象
     */
    public HttpConfig context(HttpContext context) {
        this.context = context;
        return this;
    }

    /**
     * @param inEncoding 输入编码
     * @return 返回当前对象
     */
    public HttpConfig inEncoding(String inEncoding) {
        this.inEncoding = inEncoding;
        return this;
    }

    /**
     * @param outEncoding 输出编码
     * @return 返回当前对象
     */
    public HttpConfig outEncoding(String outEncoding) {
        this.outEncoding = outEncoding;
        return this;
    }

    /**
     * 设置超时时间
     *
     * @param timeout 超时时间，单位:秒
     */
    public HttpConfig timeout(int timeout) {
        return timeout(timeout, true);
    }

    /**
     * 设置超时时间以及是否允许网页重定向（自动跳转 302）
     *
     * @param timeout        超时时间，单位:秒
     * @param redirectEnable 自动跳转
     */
    public HttpConfig timeout(int timeout, boolean redirectEnable) {
        // 修正超时时间
        if (timeout <= 0) {
            timeout = this.defaultTimeout;
        }
        int reqTimeout = Converts.toInt(TimeUnit.SECONDS.toMillis(timeout));
        // 配置请求的超时设置
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(reqTimeout)
                .setConnectTimeout(reqTimeout)
                .setSocketTimeout(reqTimeout)
                .setRedirectsEnabled(redirectEnable)
                .build();
        return timeout(config);
    }

    /**
     * 设置代理、超时时间、允许网页重定向等
     *
     * @param requestConfig 超时时间，单位：秒
     */
    public HttpConfig timeout(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
        return this;
    }

    public HttpClient getClient() {return client;}

    public Header[] getHeaders() {
        return headers;
    }

    public boolean isReturnRespHeaders() {
        return isReturnRespHeaders;
    }

    public HttpContext getContext() {
        return context;
    }

    public String getInEncoding() {
        return inEncoding;
    }

    public String getOutEncoding() {
        return outEncoding;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

}
