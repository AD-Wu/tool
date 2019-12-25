package com.x.commons.util.http.factory;

import com.arronlong.httpclientutil.common.Utils;
import com.x.commons.enums.Charset;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.http.enums.HTTPMethod;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.protocol.HttpContext;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Date 2019-12-25 22:21
 * @Author AD
 */
public class HttpConfig {
    
    // ------------------------ 变量定义 ------------------------
    
    private HttpClient client;
    
    private Header[] headers;
    
    // 用于cookie操作
    private HttpContext context;
    
    // 设置RequestConfig
    private RequestConfig requestConfig;
    
    // 是否返回响应头部信息
    private boolean isReturnRespHeaders;
    
    private String method = HTTPMethod.GET.getMethod();
    
    private String methodName;
    
    // 以json格式作为输入参数
    private String json;
    
    // 输入输出编码
    private String encoding = Charset.UTF8;
    
    // 输入编码
    private String inEncoding;
    
    // 输出编码
    private String outEncoding;
    
    // 解决多线程下载时，stream被close的问题
    private static final ThreadLocal<OutputStream> outs = new ThreadLocal<OutputStream>();
    
    // 解决多线程处理时，url被覆盖问题
    private static final ThreadLocal<String> urls = new ThreadLocal<String>();
    
    // 解决多线程处理时，url被覆盖问题
    private static final ThreadLocal<Map<String, Object>> maps = new ThreadLocal<Map<String, Object>>();
    
    // ------------------------ 构造方法 ------------------------
    
    private HttpConfig() {}
    
    public static HttpConfig of() {
        return new HttpConfig();
    }
    
    // ------------------------ 方法定义 ------------------------
    
    /**
     * @param client HttpClient对象
     *
     * @return 返回当前对象
     */
    public HttpConfig client(HttpClient client) {
        this.client = client;
        return this;
    }
    
    /**
     * @param url 资源url
     *
     * @return 返回当前对象
     */
    public HttpConfig url(String url) {
        urls.set(url);
        return this;
    }
    
    /**
     * @param headers Header头信息
     *
     * @return 返回当前对象
     */
    public HttpConfig headers(Header[] headers) {
        this.headers = headers;
        return this;
    }
    
    /**
     * Header头信息(是否返回response中的headers)
     *
     * @param headers             Header头信息
     * @param isReturnRespHeaders 是否返回response中的headers
     *
     * @return 返回当前对象
     */
    public HttpConfig headers(Header[] headers, boolean isReturnRespHeaders) {
        this.headers = headers;
        this.isReturnRespHeaders = isReturnRespHeaders;
        return this;
    }
    
    /**
     * @param method 请求方法
     *
     * @return 返回当前对象
     */
    public HttpConfig method(HTTPMethod method) {
        this.method = method.getMethod();
        return this;
    }
    
    /**
     * @param methodName 请求方法
     *
     * @return 返回当前对象
     */
    public HttpConfig methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }
    
    /**
     * @param context cookie操作相关
     *
     * @return 返回当前对象
     */
    public HttpConfig context(HttpContext context) {
        this.context = context;
        return this;
    }
    
    /**
     * @param map 传递参数
     *
     * @return 返回当前对象
     */
    public HttpConfig map(Map<String, Object> map) {
        Map<String, Object> m = maps.get();
        if (m == null || m == null || map == null) {
            m = map;
        } else {
            m.putAll(map);
        }
        maps.set(m);
        return this;
    }
    
    /**
     * @param json 以json格式字符串作为参数
     *
     * @return 返回当前对象
     */
    public HttpConfig json(String json) {
        this.json = json;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Utils.ENTITY_JSON, json);
        maps.set(map);
        return this;
    }
    
    /**
     * @param filePaths 待上传文件所在路径
     *
     * @return 返回当前对象
     */
    public HttpConfig files(String[] filePaths) {
        return files(filePaths, "file");
    }
    
    /**
     * 上传文件时用到
     *
     * @param filePaths 待上传文件所在路径
     * @param inputName 即file input 标签的name值，默认为file
     *
     * @return 返回当前对象
     */
    public HttpConfig files(String[] filePaths, String inputName) {
        return files(filePaths, inputName, false);
    }
    
    /**
     * 上传文件时用到
     *
     * @param filePaths                     待上传文件所在路径
     * @param inputName                     即file input 标签的name值，默认为file
     * @param forceRemoveContentTypeCharset 是否强制一处content-type中设置的编码类型
     *
     * @return 返回当前对象
     */
    public HttpConfig files(String[] filePaths, String inputName, boolean forceRemoveContentTypeCharset) {
        
        Map<String, Object> map = maps.get();
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        map.put(Utils.ENTITY_MULTIPART, filePaths);
        map.put(Utils.ENTITY_MULTIPART + ".name", inputName);
        map.put(Utils.ENTITY_MULTIPART + ".rmCharset", forceRemoveContentTypeCharset);
        maps.set(map);
        return this;
    }
    
    /**
     * @param encoding 输入输出编码
     *
     * @return 返回当前对象
     */
    public HttpConfig encoding(String encoding) {
        //设置输入输出
        inEncoding(encoding);
        outEncoding(encoding);
        this.encoding = encoding;
        return this;
    }
    
    /**
     * @param inEncoding 输入编码
     *
     * @return 返回当前对象
     */
    public HttpConfig inEncoding(String inEncoding) {
        this.inEncoding = inEncoding;
        return this;
    }
    
    /**
     * @param outEncoding 输出编码
     *
     * @return 返回当前对象
     */
    public HttpConfig outEncoding(String outEncoding) {
        this.outEncoding = outEncoding;
        return this;
    }
    
    /**
     * @param out 输出流对象
     */
    public HttpConfig getOutputStream(OutputStream out) {
        outs.set(out);
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
            timeout = 5;
        }
        int reqTimeout = Converts.toInt(TimeUnit.SECONDS.toMillis(timeout));
        // 配置请求的超时设置
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(reqTimeout)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
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
    
    public HttpClient getClient() {
        return client;
    }
    
    public Header[] getHeaders() {
        return headers;
    }
    
    public boolean isReturnRespHeaders() {
        return isReturnRespHeaders;
    }
    
    public String getURL() {
        return urls.get();
    }
    
    public String getMethod() {
        return method;
    }
    
    public String getMethodName() {
        return methodName;
    }
    
    public HttpContext getContext() {
        return context;
    }
    
    public Map<String, Object> map() {
        return maps.get();
    }
    
    public String getJson() {
        return json;
    }
    
    public String getEncoding() {
        return encoding;
    }
    
    public String getInEncoding() {
        return inEncoding == null ? encoding : inEncoding;
    }
    
    public String getOutEncoding() {
        return outEncoding == null ? encoding : outEncoding;
    }
    
    public OutputStream getOutputStream() {
        return outs.get();
    }
    
    public RequestConfig getRequestConfig() {
        return requestConfig;
    }
    
}
