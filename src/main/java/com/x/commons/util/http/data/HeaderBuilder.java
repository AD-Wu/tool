package com.x.commons.util.http.data;

import com.x.commons.util.bean.New;
import com.x.commons.util.http.enums.HTTPKey;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc TODO
 * @Date 2019-11-17 10:09
 * @Author AD
 */
public class HeaderBuilder {

    private Map<String, String> map;

    public HeaderBuilder() {
        this.map = New.map();
    }

    /**
     * TODO 返回header头信息
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
     * TODO 自定义header头信息
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
     * TODO 指定客户端能够接收的内容类型
     * 如：Accept: text/plain, text/html
     *
     * @param accept accept
     * @return 返回当前对象
     */
    public HeaderBuilder accept(String accept) {
        map.put(HTTPKey.ACCEPT, accept);
        return this;
    }

    /**
     * TODO 浏览器可以接受的字符编码集
     * 如：Accept-Charset: iso-8859-5
     *
     * @param acceptCharset accept-charset
     * @return 返回当前对象
     */
    public HeaderBuilder acceptCharset(String acceptCharset) {
        map.put(HTTPKey.ACCEPT_CHARSET, acceptCharset);
        return this;
    }

    /**
     * TODO 指定浏览器可以支持的web服务器返回内容压缩编码类型
     * 如：Accept-Encoding: compress, gzip
     *
     * @param acceptEncoding accept-encoding
     * @return 返回当前对象
     */
    public HeaderBuilder acceptEncoding(String acceptEncoding) {
        map.put(HTTPKey.ACCEPT_ENCODING, acceptEncoding);
        return this;
    }

    /**
     * TODO 浏览器可接受的语言
     * 如：Accept-Language: en,zh
     *
     * @param acceptLanguage accept-language
     * @return 返回当前对象
     */
    public HeaderBuilder acceptLanguage(String acceptLanguage) {
        map.put(HTTPKey.ACCEPT_LANGUAGE, acceptLanguage);
        return this;
    }

    /**
     * TODO 可以请求网页实体的一个或者多个子范围字段
     * 如：Accept-Ranges: bytes
     *
     * @param acceptRanges accept-ranges
     * @return 返回当前对象
     */
    public HeaderBuilder acceptRanges(String acceptRanges) {
        map.put(HTTPKey.ACCEPT_RANGES, acceptRanges);
        return this;
    }

    /**
     * TODO HTTP授权的授权证书
     * 如：Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
     *
     * @param authorization authorization
     * @return 返回当前对象
     */
    public HeaderBuilder authorization(String authorization) {
        map.put(HTTPKey.AUTHORIZATION, authorization);
        return this;
    }

    /**
     * TODO 指定请求和响应遵循的缓存机制
     * 如：Cache-Control: no-cache
     *
     * @param cacheControl cache-control
     * @return 返回当前对象
     */
    public HeaderBuilder cacheControl(String cacheControl) {
        map.put(HTTPKey.CACHE_CONTROL, cacheControl);
        return this;
    }

    /**
     * TODO 表示是否需要持久连接（HTTP 1.1默认进行持久连接）
     * 如：Connection: close 短链接； Connection: keep-alive 长连接
     *
     * @param connection connection
     * @return 返回当前对象
     */
    public HeaderBuilder connection(String connection) {
        map.put(HTTPKey.CONNECTION, connection);
        return this;
    }

    /**
     * TODO HTTP请求发送时，会把保存在该请求域名下的所有cookie值一起发送给web服务器
     * 如：Cookie: $Version=1; Skin=new;
     *
     * @param cookie cookie
     * @return 返回当前对象
     */
    public HeaderBuilder cookie(String cookie) {
        map.put(HTTPKey.COOKIE, cookie);
        return this;
    }

    /**
     * TODO 请求内容长度
     * 如：Content-Length: 348
     *
     * @param contentLength content-length
     * @return 返回当前对象
     */
    public HeaderBuilder contentLength(String contentLength) {
        map.put(HTTPKey.CONTENT_LENGTH, contentLength);
        return this;
    }

    /**
     * TODO 请求的与实体对应的MIME信息
     * 如：Content-Type: application/x-www-form-urlencoded
     *
     * @param contentType content-type
     * @return 返回当前对象
     */
    public HeaderBuilder contentType(String contentType) {
        map.put(HTTPKey.CONTENT_TYPE, contentType);
        return this;
    }

    /**
     * TODO 请求发送的日期和时间
     * 如：Date: Tue, 15 Nov 2010 08:12:31 GMT
     *
     * @param date date
     * @return 返回当前对象
     */
    public HeaderBuilder date(String date) {
        map.put(HTTPKey.DATE, date);
        return this;
    }

    /**
     * TODO 请求的特定的服务器行为
     * 如：Expect: 100-continue
     *
     * @param expect expect
     * @return 返回当前对象
     */
    public HeaderBuilder expect(String expect) {
        map.put(HTTPKey.EXPECT, expect);
        return this;
    }

    /**
     * TODO 发出请求的用户的Email
     * 如：From: user@email.com
     *
     * @param email
     * @return 返回当前对象
     */
    public HeaderBuilder from(String email) {
        map.put(HTTPKey.FROM, email);
        return this;
    }

    /**
     * TODO 指定请求的服务器的域名和端口号
     * 如：Host: blog.csdn.net
     *
     * @param host host
     * @return 返回当前对象
     */
    public HeaderBuilder host(String host) {
        map.put(HTTPKey.HOST, host);
        return this;
    }

    /**
     * TODO 只有请求内容与实体相匹配才有效
     * 如：If-Match: “737060cd8c284d8af7ad3082f209582d”
     *
     * @param ifMatch if-match
     * @return 返回当前对象
     */
    public HeaderBuilder ifMatch(String ifMatch) {
        map.put(HTTPKey.IF_MATCH, ifMatch);
        return this;
    }

    /**
     * TODO 如果请求的部分在指定时间之后被修改则请求成功，未被修改则返回304代码
     * 如：If-Modified-Since: Sat, 29 Oct 2010 19:43:31 GMT
     *
     * @param ifModifiedSince if-modified-Since
     * @return 返回当前对象
     */
    public HeaderBuilder ifModifiedSince(String ifModifiedSince) {
        map.put(HTTPKey.IF_MODIFIED_SINCE, ifModifiedSince);
        return this;
    }

    /**
     * TODO 如果内容未改变返回304代码，参数为服务器先前发送的Etag，与服务器回应的Etag比较判断是否改变
     * 如：If-None-Match: “737060cd8c284d8af7ad3082f209582d”
     *
     * @param ifNoneMatch if-none-match
     * @return 返回当前对象
     */
    public HeaderBuilder ifNoneMatch(String ifNoneMatch) {
        map.put(HTTPKey.IF_NONE_MATCH, ifNoneMatch);
        return this;
    }

    /**
     * TODO 如果实体未改变，服务器发送客户端丢失的部分，否则发送整个实体。参数也为Etag
     * 如：If-Range: “737060cd8c284d8af7ad3082f209582d”
     *
     * @param ifRange if-range
     * @return 返回当前对象
     */
    public HeaderBuilder ifRange(String ifRange) {
        map.put(HTTPKey.IF_RANGE, ifRange);
        return this;
    }

    /**
     * TODO 只在实体在指定时间之后未被修改才请求成功
     * 如：If-Unmodified-Since: Sat, 29 Oct 2010 19:43:31 GMT
     *
     * @param ifUnmodifiedSince if-unmodified-since
     * @return 返回当前对象
     */
    public HeaderBuilder ifUnmodifiedSince(String ifUnmodifiedSince) {
        map.put(HTTPKey.IF_UNMODIFIED_SINCE, ifUnmodifiedSince);
        return this;
    }

    /**
     * TODO 限制信息通过代理和网关传送的时间
     * 如：Max-Forwards: 10
     *
     * @param maxForwards max-forwards
     * @return 返回当前对象
     */
    public HeaderBuilder maxForwards(String maxForwards) {
        map.put(HTTPKey.MAX_FORWARDS, maxForwards);
        return this;
    }

    /**
     * TODO 用来包含实现特定的指令
     * 如：Pragma: no-cache
     *
     * @param pragma pragma
     * @return 返回当前对象
     */
    public HeaderBuilder pragma(String pragma) {
        map.put(HTTPKey.PRAGMA, pragma);
        return this;
    }

    /**
     * TODO 连接到代理的授权证书
     * 如：Proxy-Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
     *
     * @param proxyAuthorization proxy-authorization
     * @return 返回当前对象
     */
    public HeaderBuilder proxyAuthorization(String proxyAuthorization) {
        map.put(HTTPKey.PROXY_AUTHORIZATION, proxyAuthorization);
        return this;
    }

    /**
     * TODO 只请求实体的一部分，指定范围
     * 如：Range: bytes=500-999
     *
     * @param range range
     * @return 返回当前对象
     */
    public HeaderBuilder range(String range) {
        map.put(HTTPKey.RANGE, range);
        return this;
    }

    /**
     * TODO 先前网页的地址，当前请求网页紧随其后,即来路
     * 如：Referer: http://www.zcmhi.com/archives/71.html
     *
     * @param referer referer
     * @return 返回当前对象
     */
    public HeaderBuilder referer(String referer) {
        map.put(HTTPKey.REFERER, referer);
        return this;
    }

    /**
     * TODO 客户端愿意接受的传输编码，并通知服务器接受接受尾加头信息
     * 如：TE: trailers,deflate;q=0.5
     *
     * @param te te
     * @return 返回当前对象
     */
    public HeaderBuilder te(String te) {
        map.put(HTTPKey.TE, te);
        return this;
    }

    /**
     * TODO 向服务器指定某种传输协议以便服务器进行转换（如果支持）
     * 如：Upgrade: HTTP/2.0, SHTTP/1.3, IRC/6.9, RTA/x11
     *
     * @param upgrade upgrade
     * @return 返回当前对象
     */
    public HeaderBuilder upgrade(String upgrade) {
        map.put(HTTPKey.UPGRADE, upgrade);
        return this;
    }

    /**
     * TODO User-Agent的内容包含发出请求的用户信息
     *
     * @param userAgent user-agent
     * @return 返回当前对象
     */
    public HeaderBuilder userAgent(String userAgent) {
        map.put(HTTPKey.USER_AGENT, userAgent);
        return this;
    }

    /**
     * TODO 关于消息实体的警告信息
     * 如：Warn: 199 Miscellaneous warning
     *
     * @param warning warning
     * @return 返回当前对象
     */
    public HeaderBuilder warning(String warning) {
        map.put(HTTPKey.WARNING, warning);
        return this;
    }

    /**
     * TODO 通知中间网关或代理服务器地址，通信协议
     * 如：Via: 1.0 fred, 1.1 nowhere.com (Apache/1.1)
     *
     * @param via via
     * @return 返回当前对象
     */
    public HeaderBuilder via(String via) {
        map.put(HTTPKey.VIA, via);
        return this;
    }

    /**
     * TODO 设置此HTTP连接的持续时间（超时时间）
     * 如：Keep-Alive: 300
     *
     * @param keepAlive keep-alive
     * @return 返回当前对象
     */
    public HeaderBuilder keepAlive(String keepAlive) {
        map.put(HTTPKey.KEEP_ALIVE, keepAlive);
        return this;
    }

}
