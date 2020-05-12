package com.x.commons.util.http.factory;

import com.x.commons.ssl.SSL;
import com.x.commons.ssl.enums.SSLVersion;
import com.x.commons.util.string.Strings;
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
import org.apache.http.impl.client.HttpClients;
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
public final class HttpClientFactory {

    // -------------------------- 成员变量 --------------------------

    // -------------------------- 构造方法 --------------------------

    private HttpClientFactory() {}

    // -------------------------- 成员方法 --------------------------

    /**
     * 获取默认的http请求客户端
     *
     * @return
     */
    public static HttpClient http() {
        return getDefaultClient();
    }

    /**
     * 获取默认的https请求客户端
     *
     * @return
     */
    public static HttpClient https() {
        return HttpClients.createDefault();
    }

    /**
     * 获取自定义http请求客户端
     *
     * @param builder 自定义构建器：连接池、重试次数、代理
     * @return
     */
    public static HttpClient http(Builder builder) {
        return builder.getHttpClientBuilder().build();
    }

    /**
     * 获取指定SSL版本的https请求客户端
     *
     * @param version SSL版本
     * @return
     */
    public static HttpClient http(SSLVersion version) {
        SSL ssl = new SSL(version);
        SSLConnectionSocketFactory factory = ssl.getSSLConnSocketFactory();
        HttpClientBuilder builder = getDefaultBuilder();
        return builder.setSSLSocketFactory(factory).build();
    }

    /**
     * 获取指定SSL版本的https请求客户端
     *
     * @param version SSL版本
     * @param builder 自定义构建器：连接池、重试次数、代理
     * @return
     */
    public static HttpClient http(SSLVersion version, Builder builder) {
        SSL ssl = new SSL(version);
        SSLConnectionSocketFactory factory = ssl.getSSLConnSocketFactory();
        HttpClientBuilder client = builder.getHttpClientBuilder();
        return client.setSSLSocketFactory(factory).build();
    }

    /**
     * 获取指定指定证书的https请求客户端
     *
     * @param keyStorePath 证书路径
     * @return
     */
    public static HttpClient http(String keyStorePath) throws Exception {
        return http(keyStorePath, "no-password", new Builder());
    }

    /**
     * 获取指定指定证书的https请求客户端
     *
     * @param keyStorePath 证书路径
     * @param builder      自定义构建器：连接池、重试次数、代理
     * @return
     */
    public static HttpClient http(String keyStorePath, String keyStorepass, Builder builder) throws Exception {
        if (!Strings.isNull(keyStorePath)) {
            SSL ssl = new SSL(keyStorePath, keyStorepass);
            SSLConnectionSocketFactory factory = ssl.getSSLConnSocketFactory();
            HttpClientBuilder client = builder.getHttpClientBuilder();
            return client.setSSLSocketFactory(factory).build();
        }
        throw new Exception("the keyStorePath is null");
    }

    private static HttpClientBuilder getDefaultBuilder() {
        return new Builder().getHttpClientBuilder();
    }

    private static HttpClient getDefaultClient() {
        return getDefaultBuilder().build();
    }

    // -------------------------- 构建器 --------------------------

    public static class Builder {

        private final HttpClientBuilder builder;

        private static final int MAX_POOL_SIZE = 20;

        private static final int MAX_PER_ROUTE = 20;

        public Builder() {
            this.builder = HttpClientBuilder.create();
            this.pool(MAX_POOL_SIZE, MAX_PER_ROUTE);
        }

        public Builder retry(int tryCount,boolean retryWhenIOInterrupted) {
            builder.setRetryHandler(this.getRetryHandler(tryCount, retryWhenIOInterrupted));
            return this;
        }

        public Builder proxy(String ip, int port) {
            // 依次是代理地址，代理端口号，协议类型
            HttpHost proxy = new HttpHost(ip, port, "http");
            builder.setRoutePlanner(new DefaultProxyRoutePlanner(proxy));
            return this;
        }

        public Builder pool(int maxTotal, int maxPerRoute) {
            Registry<ConnectionSocketFactory> registry = RegistryBuilder
                    .<ConnectionSocketFactory> create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSL(SSLVersion.SSLv3).getSSLConnSocketFactory()).build();
            // 设置连接池大小
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                    registry);
            connManager.setMaxTotal(maxTotal);
            connManager.setDefaultMaxPerRoute(maxPerRoute);
            this.builder.setConnectionManager(connManager);
            return this;
        }

        private HttpClientBuilder getHttpClientBuilder() {
            return builder;
        }

        // -------------------------- 私有方法 --------------------------

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
                    return !(request instanceof HttpEntityEnclosingRequest);
                }
            };
        }

    }

}
