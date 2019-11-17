package com.x.commons.util.http.data;

import com.x.commons.util.bean.New;
import lombok.NonNull;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @Desc TODO
 * @Date 2019-11-17 12:26
 * @Author AD
 */
public class HTTPResponse implements Serializable {

    // ----------------------- 成员变量 -----------------------

    /**
     * 状态码-statusCode
     */
    private final int statusCode;

    /**
     * 状态行-StatusLine
     */
    private final StatusLine statusLine;

    /**
     * 响应头信息
     */
    private final Header[] respHeaders;

    /**
     * 协议版本
     */
    private final ProtocolVersion protocolVersion;

    /**
     * 执行结果-body
     */
    private String result;

    /**
     * 请求头信息
     */
    private Header[] reqHeaders;

    private Map<String, String> respHeaderMap;

    // ----------------------- 构造方法 -----------------------

    public HTTPResponse(@NonNull HttpResponse resp) {
        this.statusLine = resp.getStatusLine();
        this.respHeaders = resp.getAllHeaders();
        this.protocolVersion = resp.getProtocolVersion();
        this.statusCode = resp.getStatusLine().getStatusCode();
        respHeaderMap = New.map();
        Stream.of(respHeaders).forEach(h -> {
            respHeaderMap.put(h.getName(), h.getValue());
        });
    }

    // ----------------------- 成员方法 -----------------------

    /**
     * TODO 获取响应头部指定信息
     *
     * @param key
     * @return
     */
    public String getRespHeader(String key) {
        return respHeaderMap.get(key);
    }

    /**
     * 获取  执行结果-body
     */
    public String getResult() {
        return this.result;
    }

    /**
     * 设置  执行结果-body
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 获取  状态码-statusCode
     */
    public int getStatusCode() {
        return this.statusCode;
    }

    /**
     * 获取  状态行-StatusLine
     */
    public StatusLine getStatusLine() {
        return this.statusLine;
    }

    /**
     * 获取  请求头信息
     */
    public Header[] getReqHeaders() {
        return this.reqHeaders;
    }

    /**
     * 设置  请求头信息
     */
    public void setReqHeaders(Header[] reqHeaders) {
        this.reqHeaders = reqHeaders;
    }

    /**
     * 获取  响应头信息
     */
    public Header[] getRespHeaders() {
        return this.respHeaders;
    }

    /**
     * 获取  协议版本
     */
    public ProtocolVersion getProtocolVersion() {
        return this.protocolVersion;
    }

}
