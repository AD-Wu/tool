package com.x.commons.util.http.factory;

import com.x.commons.enums.Charset;
import com.x.commons.util.bean.New;
import com.x.commons.util.http.enums.HttpHeaderKey;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc
 * @Date 2019-11-17 10:09
 * @Author AD
 */
public class HeaderBuilder {

    private Map<String, String> map;

    public HeaderBuilder() {
        this.map = New.map();
    }

    /**
     * 默认创建接收UTF-8编码的header
     */
    public static Header[] defaultBuild() {
        HeaderBuilder builder = new HeaderBuilder();
        return builder.acceptCharset(Charset.UTF8).build();
    }

    /**
     * 默认创建接收UTF-8编码的header（需身份认证）
     *
     * @param authorization 加密过后的身份认证
     * @return
     */
    public static Header[] authorizationBuild(String authorization) {
        HeaderBuilder builder = new HeaderBuilder();
        return builder.acceptCharset(Charset.UTF8).authorization(authorization).build();
    }

    /**
     * 返回header头信息
     *
     * @return 返回构建的header头信息数组
     */
    public Header[] build() {
        Header[] headers = new Header[map.size()];
        AtomicInteger i = new AtomicInteger(0);
        map.forEach((k, v) -> {
            BasicHeader header = new BasicHeader(k, v);
            headers[i.getAndIncrement()] = header;
        });
        return headers;
    }

    /**
     * 自定义header头信息
     *
     * @param key   header-key
     * @param value header-value
     * @return 返回当前对象
     */
    public HeaderBuilder other(String key, String value) {
        map.put(key, value);
        return this;
    }

    /**
     * 指定客户端能够接收的内容类型
     * 如：Accept: text/plain, text/html
     *
     * @param accept accept
     * @return 返回当前对象
     */
    public HeaderBuilder accept(String accept) {
        map.put(HttpHeaderKey.ACCEPT, accept);
        return this;
    }

    /**
     * 浏览器可以接受的字符编码集
     * 如：Accept-Charset: iso-8859-5
     *
     * @param acceptCharset accept-charset
     * @return 返回当前对象
     */
    public HeaderBuilder acceptCharset(String acceptCharset) {
        map.put(HttpHeaderKey.ACCEPT_CHARSET, acceptCharset);
        return this;
    }

    /**
     * 指定浏览器可以支持的web服务器返回内容压缩编码类型
     * 如：Accept-Encoding: compress, gzip
     *
     * @param acceptEncoding accept-encoding
     * @return 返回当前对象
     */
    public HeaderBuilder acceptEncoding(String acceptEncoding) {
        map.put(HttpHeaderKey.ACCEPT_ENCODING, acceptEncoding);
        return this;
    }

    /**
     * 浏览器可接受的语言
     * 如：Accept-Language: en-US,zh-CN
     *
     * @param acceptLanguage accept-language
     * @return 返回当前对象
     */
    public HeaderBuilder acceptLanguage(String acceptLanguage) {
        map.put(HttpHeaderKey.ACCEPT_LANGUAGE, acceptLanguage);
        return this;
    }

    /**
     * 可以请求网页实体的一个或者多个子范围字段
     * 如：Accept-Ranges: bytes
     *
     * @param acceptRanges accept-ranges
     * @return 返回当前对象
     */
    public HeaderBuilder acceptRanges(String acceptRanges) {
        map.put(HttpHeaderKey.ACCEPT_RANGES, acceptRanges);
        return this;
    }

    /**
     * HTTP授权的授权证书
     * 如：Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
     *
     * @param authorization 加密过后的密码
     * @return 返回当前对象
     */
    public HeaderBuilder authorization(String authorization) {
        map.put(HttpHeaderKey.AUTHORIZATION, authorization);
        return this;
    }

    /**
     * 指定请求和响应遵循的缓存机制
     * 如：Cache-Control: no-cache
     *
     * @param cacheControl cache-control
     * @return 返回当前对象
     */
    public HeaderBuilder cacheControl(String cacheControl) {
        map.put(HttpHeaderKey.CACHE_CONTROL, cacheControl);
        return this;
    }

    /**
     * 表示是否需要持久连接（HTTP 1.1默认进行持久连接）
     * 如：Connection: close 短链接； Connection: keep-alive 长连接
     *
     * @param connection connection
     * @return 返回当前对象
     */
    public HeaderBuilder connection(String connection) {
        map.put(HttpHeaderKey.CONNECTION, connection);
        return this;
    }

    /**
     * HTTP请求发送时，会把保存在该请求域名下的所有cookie值一起发送给web服务器
     * 如：Cookie: $Version=1; Skin=new;
     *
     * @param cookie cookie
     * @return 返回当前对象
     */
    public HeaderBuilder cookie(String cookie) {
        map.put(HttpHeaderKey.COOKIE, cookie);
        return this;
    }

    /**
     * 请求内容长度
     * 如：Content-Length: 348
     *
     * @param contentLength content-length
     * @return 返回当前对象
     */
    public HeaderBuilder contentLength(String contentLength) {
        map.put(HttpHeaderKey.CONTENT_LENGTH, contentLength);
        return this;
    }

    /**
     * 请求的与实体对应的MIME信息
     * 如：Content-Type: application/x-www-form-urlencoded
     *
     * @param contentType content-type
     * @return 返回当前对象
     */
    public HeaderBuilder contentType(String contentType) {
        map.put(HttpHeaderKey.CONTENT_TYPE, contentType);
        return this;
    }

    /**
     * 请求发送的日期和时间
     * 如：Date: Tue, 15 Nov 2010 08:12:31 GMT
     *
     * @param date date
     * @return 返回当前对象
     */
    public HeaderBuilder date(String date) {
        map.put(HttpHeaderKey.DATE, date);
        return this;
    }

    /**
     * 请求的特定的服务器行为
     * 如：Expect: 100-continue
     *
     * @param expect expect
     * @return 返回当前对象
     */
    public HeaderBuilder expect(String expect) {
        map.put(HttpHeaderKey.EXPECT, expect);
        return this;
    }

    /**
     * 发出请求的用户的Email
     * 如：From: user@email.com
     *
     * @param email
     * @return 返回当前对象
     */
    public HeaderBuilder from(String email) {
        map.put(HttpHeaderKey.FROM, email);
        return this;
    }

    /**
     * 指定请求的服务器的域名和端口号
     * 如：Host: blog.csdn.net
     *
     * @param host host
     * @return 返回当前对象
     */
    public HeaderBuilder host(String host) {
        map.put(HttpHeaderKey.HOST, host);
        return this;
    }

    /**
     * 只有请求内容与实体相匹配才有效
     * 如：If-Match: “737060cd8c284d8af7ad3082f209582d”
     *
     * @param ifMatch if-match
     * @return 返回当前对象
     */
    public HeaderBuilder ifMatch(String ifMatch) {
        map.put(HttpHeaderKey.IF_MATCH, ifMatch);
        return this;
    }

    /**
     * 如果请求的部分在指定时间之后被修改则请求成功，未被修改则返回304代码
     * 如：If-Modified-Since: Sat, 29 Oct 2010 19:43:31 GMT
     *
     * @param ifModifiedSince if-modified-Since
     * @return 返回当前对象
     */
    public HeaderBuilder ifModifiedSince(String ifModifiedSince) {
        map.put(HttpHeaderKey.IF_MODIFIED_SINCE, ifModifiedSince);
        return this;
    }

    /**
     * 如果内容未改变返回304代码，参数为服务器先前发送的Etag，与服务器回应的Etag比较判断是否改变
     * 如：If-None-Match: “737060cd8c284d8af7ad3082f209582d”
     *
     * @param ifNoneMatch if-none-match
     * @return 返回当前对象
     */
    public HeaderBuilder ifNoneMatch(String ifNoneMatch) {
        map.put(HttpHeaderKey.IF_NONE_MATCH, ifNoneMatch);
        return this;
    }

    /**
     * 如果实体未改变，服务器发送客户端丢失的部分，否则发送整个实体。参数也为Etag
     * 如：If-Range: “737060cd8c284d8af7ad3082f209582d”
     *
     * @param ifRange if-range
     * @return 返回当前对象
     */
    public HeaderBuilder ifRange(String ifRange) {
        map.put(HttpHeaderKey.IF_RANGE, ifRange);
        return this;
    }

    /**
     * 只在实体在指定时间之后未被修改才请求成功
     * 如：If-Unmodified-Since: Sat, 29 Oct 2010 19:43:31 GMT
     *
     * @param ifUnmodifiedSince if-unmodified-since
     * @return 返回当前对象
     */
    public HeaderBuilder ifUnmodifiedSince(String ifUnmodifiedSince) {
        map.put(HttpHeaderKey.IF_UNMODIFIED_SINCE, ifUnmodifiedSince);
        return this;
    }

    /**
     * 限制信息通过代理和网关传送的时间
     * 如：Max-Forwards: 10
     *
     * @param maxForwards max-forwards
     * @return 返回当前对象
     */
    public HeaderBuilder maxForwards(String maxForwards) {
        map.put(HttpHeaderKey.MAX_FORWARDS, maxForwards);
        return this;
    }

    /**
     * 用来包含实现特定的指令
     * 如：Pragma: no-cache
     *
     * @param pragma pragma
     * @return 返回当前对象
     */
    public HeaderBuilder pragma(String pragma) {
        map.put(HttpHeaderKey.PRAGMA, pragma);
        return this;
    }

    /**
     * 连接到代理的授权证书
     * 如：Proxy-Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
     *
     * @param proxyAuthorization proxy-authorization
     * @return 返回当前对象
     */
    public HeaderBuilder proxyAuthorization(String proxyAuthorization) {
        map.put(HttpHeaderKey.PROXY_AUTHORIZATION, proxyAuthorization);
        return this;
    }

    /**
     * 只请求实体的一部分，指定范围
     * 如：Range: bytes=500-999
     *
     * @param range range
     * @return 返回当前对象
     */
    public HeaderBuilder range(String range) {
        map.put(HttpHeaderKey.RANGE, range);
        return this;
    }

    /**
     * 先前网页的地址，当前请求网页紧随其后,即来路
     * 如：Referer: http://www.zcmhi.com/archives/71.html
     *
     * @param referer referer
     * @return 返回当前对象
     */
    public HeaderBuilder referer(String referer) {
        map.put(HttpHeaderKey.REFERER, referer);
        return this;
    }

    /**
     * 客户端愿意接受的传输编码，并通知服务器接受接受尾加头信息
     * 如：TE: trailers,deflate;q=0.5
     *
     * @param te te
     * @return 返回当前对象
     */
    public HeaderBuilder te(String te) {
        map.put(HttpHeaderKey.TE, te);
        return this;
    }

    /**
     * 向服务器指定某种传输协议以便服务器进行转换（如果支持）
     * 如：Upgrade: HTTP/2.0, SHTTP/1.3, IRC/6.9, RTA/x11
     *
     * @param upgrade upgrade
     * @return 返回当前对象
     */
    public HeaderBuilder upgrade(String upgrade) {
        map.put(HttpHeaderKey.UPGRADE, upgrade);
        return this;
    }

    /**
     * User-Agent的内容包含发出请求的用户信息
     *
     * @param userAgent user-agent
     * @return 返回当前对象
     */
    public HeaderBuilder userAgent(String userAgent) {
        map.put(HttpHeaderKey.USER_AGENT, userAgent);
        return this;
    }

    /**
     * 关于消息实体的警告信息
     * 如：Warn: 199 Miscellaneous warning
     *
     * @param warning warning
     * @return 返回当前对象
     */
    public HeaderBuilder warning(String warning) {
        map.put(HttpHeaderKey.WARNING, warning);
        return this;
    }

    /**
     * 通知中间网关或代理服务器地址，通信协议
     * 如：Via: 1.0 fred, 1.1 nowhere.com (Apache/1.1)
     *
     * @param via via
     * @return 返回当前对象
     */
    public HeaderBuilder via(String via) {
        map.put(HttpHeaderKey.VIA, via);
        return this;
    }

    /**
     * 设置此HTTP连接的持续时间（超时时间）
     * 如：Keep-Alive: 300
     *
     * @param keepAlive keep-alive
     * @return 返回当前对象
     */
    public HeaderBuilder keepAlive(String keepAlive) {
        map.put(HttpHeaderKey.KEEP_ALIVE, keepAlive);
        return this;
    }

}
