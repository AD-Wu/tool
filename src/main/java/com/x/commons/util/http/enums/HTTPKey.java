package com.x.commons.util.http.enums;

/**
 * @Desc TODO
 * @Date 2019-11-17 10:12
 * @Author AD
 */
public class HTTPKey {

    /**
     * 浏览器申明所接受的介质类型<br/>
     * 如："type/*"表示该类型下的所有自类型，type/sub-type；type换成*表示接受任何类型
     */
    public static final String ACCEPT = "Accept";

    /**
     * 浏览器申明所接受的字符集<br/>
     * 如：GB2312，UTF-8
     */
    public static final String ACCEPT_CHARSET = "Accept-Charset";

    /**
     * 浏览器申明接收编码的方法<br/>
     * 通常指定压缩方法，是否支持压缩，支持什么压缩方法 （gzip，deflate）
     */
    public static final String ACCEPT_ENCODING = "Accept-Encoding";

    /**
     * 浏览器申明自己接收的语（语言跟字符集的区别：中文是语言，中文有多种字符集，比如big5，gb2312，gbk等等。）
     */
    public static final String ACCEPT_LANGUAGE = "Accept-Language";

    /**
     * WEB服务器表明自己是否接受获取其某个实体的一部分（比如文件的一部分）的请求。<br/>
     * bytes：表示接受，none：表示不接受。
     */
    public static final String ACCEPT_RANGES = "Accept-Ranges";

    /**
     * HTTP授权的授权证书<br/>
     * 如：Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 请求：<br/>
     * no-cache（不要缓存的实体，要求现在从WEB服务器去取）<br/>
     * max-age：（只接受 Age 值小于 max-age 值，并且没有过期的对象）<br/>
     * max-stale：（可以接受过去的对象，但是过期时间必须小于max-stale 值）<br/>
     * min-fresh：（接受其新鲜生命期大于其当前Age跟min-fresh值之和的缓存对象）<br/>
     * 响应：<br/>
     * public(可以用 Cached 内容回应任何用户)<br/>
     * private（只能用缓存内容回应先前请求该内容的那个用户）<br/>
     * no-cache（可以缓存，但是只有在跟WEB服务器验证了其有效后，才能返回给客户端）<br/>
     * max-age：（本响应包含的对象的过期时间）<br/>
     * ALL:  no-store（不允许缓存）<br/>
     */
    public static final String CACHE_CONTROL = "Cache-Control";

    /**
     * 请求：<br/>
     * close（在完成本次请求的响应后，断开连接，不要等待本次连接的后续请求了。<br/>
     * keepalive（在完成本次请求的响应后，保持连接，等待本次连接的后续请求）。<br/>
     * 响应：<br/>
     * close（连接已经关闭）。<br/>
     * keepalive（连接保持着，在等待本次连接的后续请求）。<br/>
     */
    public static final String CONNECTION = "Connection";

    /**
     * 浏览器所携带的一些验证信息等
     */
    public static final String COOKIE = "Cookie";

    /**
     * WEB 服务器告诉浏览器自己响应的对象的长度。<br/>
     * 如：Content-Length: 26012
     */
    public static final String CONTENT_LENGTH = "Content-Length";

    /**
     * 响应的对象的类型。
     * 例如：Content-Type：application/json
     */
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * 请求发送的日期和时间
     */
    public static final String DATE = "Date";

    /**
     * 请求的特定的服务器行为，如：Expect: 100-continue
     */
    public static final String EXPECT = "Expect";

    /**
     * 发出请求的用户的Email，如：From: user@email.com
     */
    public static final String FROM = "From";

    /**
     * 客户端指定自己想访问的WEB服务器的域名/IP 地址和端口号。
     */
    public static final String HOST = "Host";

    /**
     * ETag 的作用跟 Last-Modified 的作用差不多，主要供 WEB 服务器判断一个对象是否改变了。
     */
    public static final String ETAG = "ETag";

    /**
     * 如果对象的 ETag 没有改变，其实也就意味著对象没有改变，才执行请求的动作。
     */
    public static final String IF_MATCH = "If-Match ";

    /**
     * 如果对象的 ETag 改变了，其实也就意味著对象也改变了，才执行请求的动作。
     */
    public static final String IF_NONE_MATCH = "If-None-Match";

    /**
     * 如果请求的对象在该头部指定的时间之后修改了，才执行请求的动作（比如返回对象），
     * 否则返回代码304，告诉浏览器该对象没有修改。
     */
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";

    /**
     * 如果请求的对象在该头部指定的时间之后没修改过，才执行请求的动作（比如返回对象）。
     */
    public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";

    /**
     * 浏览器（比如 Flashget 多线程下载时）告诉 WEB 服务器自己想取对象的哪部分。<br/>
     * 如：Range: bytes=1173546-
     */
    public static final String RANGE = "Range";

    /**
     * 浏览器告诉 WEB 服务器，如果我请求的对象没有改变，就把我缺少的部分
     * 给我，如果对象改变了，就把整个对象给我。 浏览器通过发送请求对象的
     * ETag 或者 自己所知道的最后修改时间给 WEB 服务器，让其判断对象是否
     * 改变了。<br/>
     * 总是跟 Range 头部一起使用。
     */
    public static final String IF_RANGE = "If-Range";

    /**
     * 如果浏览器请求保持连接，则该头部表明希望 WEB 服务器保持连接多长时间（秒）。<br/>
     * 如：Keep-Alive：300
     */
    public static final String KEEP_ALIVE = "Keep-Alive";

    /**
     * 限制信息通过代理和网关传送的时间，如：Max-Forwards:10
     */
    public static final String MAX_FORWARDS = "Max-Forwards";

    /**
     * 主要使用 Pramga: no-cache，相当于 Cache-Control： no-cache。
     */
    public static final String PRAGMA = "Pragma";

    /**
     * Proxy-Authorization：浏览器响应代理服务器的身份验证请求，提供自己的身份信息。<br/>
     * Proxy-Authenticate： 代理服务器响应浏览器，要求其提供代理身份验证信息。
     */
    public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";

    /**
     * 浏览器向WEB服务器表明自己是从哪个网址(URL)获得当前请求中的网址。
     */
    public static final String REFERER = "Referer";

    /**
     * Transfer-Encoding 消息首部指明了将 entity 安全传递给用户所采用的编码形式。
     */
    public static final String TE = "TE";

    /**
     * 向服务器指定某种传输协议以便服务器进行转换（如果支持）<br/>
     * 如：Upgrade: HTTP/2.0, SHTTP/1.3, IRC/6.9, RTA/x11
     */
    public static final String UPGRADE = "Upgrade";

    /**
     * 浏览器表明自己的身份（是哪种浏览器）。
     */
    public static final String USER_AGENT = "User-Agent";

    /**
     * 列出从客户端到 OCS 或者相反方向的响应经过了哪些代理服务器，他们用什么协议（和版本）发送的请求。
     */
    public static final String VIA = "Via";

    /**
     * 关于消息实体的警告信息
     */
    public static final String WARNING = "Warning";

}
