package com.x.commons.util.http.factory;

import com.x.commons.ssl.SSL;
import com.x.commons.ssl.enums.SSLVersion;
import com.x.commons.util.convert.Strings;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * @Author AD
 * @Date 2019/11/18 9:45
 */
public class HttpClientFactory {

    // -------------------------------- 成员变量 --------------------------------

    private HttpClientBuilder builder;

    // -------------------------------- 构造方法 --------------------------------
    public HttpClientFactory() {
        builder = HttpClientBuilder.create();
    }

    public HttpClientFactory(int tryCount) {
        builder = HttpClientBuilder.create().setRetryHandler(retry(tryCount));
    }

    public HttpClientFactory(String ip, int port) {
        builder = HttpClientBuilder.create().setRoutePlanner(proxy(ip, port));
    }

    // -------------------------------- 成员方法 --------------------------------

    public HttpClient http() {
        return builder.build();
    }

    public HttpClient https(SSLVersion version) {
        SSL ssl = new SSL(version);
        SSLConnectionSocketFactory factory = ssl.getSSLConnSocketFactory();
        return builder.setSSLSocketFactory(factory).build();
    }

    public HttpClient https(String keyStorePath) throws Exception {
        return https(keyStorePath, "no-password");
    }

    public HttpClient https(String keyStorePath, String keyStorepass) throws Exception {
        if (!Strings.isNull(keyStorePath)) {
            SSL ssl = new SSL(keyStorePath, keyStorepass);
            SSLConnectionSocketFactory factory = ssl.getSSLConnSocketFactory();
            return builder.setSSLSocketFactory(factory).build();
        }
        throw new Exception("the keyStorePath is null");
    }

    // -------------------------------- 私有方法 --------------------------------

    private DefaultProxyRoutePlanner proxy(String ip, int port) {
        // 依次是代理地址，代理端口号，协议类型
        HttpHost proxy = new HttpHost(ip, port, "http");
        return new DefaultProxyRoutePlanner(proxy);
    }

    private HttpRequestRetryHandler retry(final int tryCount) {
        return retry(tryCount, false);
    }

    private HttpRequestRetryHandler retry(final int tryCount, final boolean retryWhenIOInterrupted) {
        // 请求重试处理
        return getRetryHandler(tryCount, retryWhenIOInterrupted);
    }

    private PoolingHttpClientConnectionManager pool(int maxTotal, int maxPerRoute) {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSL(SSLVersion.SSLv3).getSSLConnSocketFactory()).build();
        // 设置连接池大小
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        connManager.setMaxTotal(maxTotal);
        connManager.setDefaultMaxPerRoute(maxPerRoute);
        return connManager;
    }

    private HttpRequestRetryHandler getRetryHandler(int tryCount, boolean retryWhenIOInterrupted) {

        return new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(final IOException e, final int executeCount, final HttpContext ctx) {
                if (executeCount >= tryCount) {// 如果已经重试了n次，就放弃
                    return false;
                }
                if (e instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (e instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (e instanceof InterruptedIOException) {// 超时
                    return retryWhenIOInterrupted;
                }
                if (e instanceof UnknownHostException) {// 目标服务器不可达
                    return true;
                }
                if (e instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (e instanceof SSLException) {// SSL握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(ctx);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
    }

}
