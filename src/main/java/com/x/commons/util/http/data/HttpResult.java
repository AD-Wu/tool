package com.x.commons.util.http.data;

import com.x.commons.util.bean.New;
import com.x.commons.util.http.enums.HttpHeaderKey;
import com.x.commons.util.string.Strings;
import org.apache.http.*;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @Desc
 * @Date 2019-11-17 12:26
 * @Author AD
 */
public final class HttpResult implements Serializable {

    // ----------------------- 成员变量 -----------------------

    /**
     * 状态码-statusCode
     */
    private final int statusCode;

    /**
     * 结果-Content-Type
     */
    private final String contentType;

    /**
     * 结果编码
     */
    private final String charset;

    /**
     * 协议版本
     */
    private final ProtocolVersion protocolVersion;

    /**
     * 执行结果-body
     */
    private final byte[] result;

    /**
     * 响应结果头部信息
     */
    private final Map<String, String> respHeaders;

    // ----------------------- 构造方法 -----------------------

    public HttpResult(HttpResponse resp) throws IOException {
        this.statusCode = resp.getStatusLine().getStatusCode();
        this.protocolVersion = resp.getProtocolVersion();
        this.result = EntityUtils.toByteArray(resp.getEntity());
        respHeaders = New.map();
        Stream.of(resp.getAllHeaders()).forEach(h -> {
            respHeaders.put(h.getName(), h.getValue());
        });
        String type = respHeaders.get(HttpHeaderKey.CONTENT_TYPE);
        String[] split = type.split(";");
        if (split.length > 0) {
            this.contentType = split[0];
            if (split.length > 1) {
                String[] charsets = split[1].split("=");
                if (charsets.length > 1) {
                    this.charset = charsets[1];
                } else {
                    this.charset = null;
                }
            } else {
                this.charset = null;
            }
        } else {
            this.contentType = null;
            this.charset = null;
        }


    }

    // ----------------------- 成员方法 -----------------------

    /**
     * 获取响应头部指定信息
     *
     * @return
     */
    public Map<String, String> getRespHeaders() {
        return this.respHeaders;
    }

    /**
     * 获取  执行结果-body
     */
    public byte[] getResult() {
        return this.result;
    }

    /**
     * 获取  状态码-statusCode
     */
    public int getStatusCode() {
        return this.statusCode;
    }

    /**
     * 获取  结果-Content-Type
     */
    public String getContentType() {
        return this.contentType;
    }

    /**
     * 获取  结果编码
     */
    public String getCharset() {
        return this.charset;
    }

    /**
     * 获取  协议版本
     */
    public ProtocolVersion getProtocolVersion() {
        return this.protocolVersion;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }

}
