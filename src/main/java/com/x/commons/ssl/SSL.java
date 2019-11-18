package com.x.commons.ssl;

import com.x.commons.ssl.enums.SSLVersion;
import lombok.SneakyThrows;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

/**
 * @Desc TODO
 * @Date 2019-11-17 21:04
 * @Author AD
 */
public class SSL {

    // -------------------------- 静态变量 --------------------------

    private static final SSLVerifier VERIFIER = new SSLVerifier();

    // -------------------------- 成员变量 --------------------------

    private final SSLSocketFactory sslFactory;

    private final SSLConnectionSocketFactory sslConnFactory;

    private final SSLIOSessionStrategy sslIOSessionStrategy;

    // -------------------------- 构造方法 --------------------------

    // public SSL() {}

    /**
     * TODO 创建无证书的SSL对象
     *
     * @param version
     */
    public SSL(SSLVersion version) {
        SSLContext sslContext = getSSLContext(version);
        initSSLContext(sslContext, VERIFIER, new SecureRandom());
        this.sslFactory = sslContext.getSocketFactory();
        this.sslConnFactory = new SSLConnectionSocketFactory(sslContext, VERIFIER);
        this.sslIOSessionStrategy = new SSLIOSessionStrategy(sslContext, VERIFIER);
    }

    /**
     * TODO 创建有证书的SSL对象
     *
     * @param keyStorePath
     * @param keyStorepass
     */
    public SSL(String keyStorePath, String keyStorepass) {
        SSLContext sslContext = getSSLContextWithKey(keyStorePath, keyStorepass);
        initSSLContext(sslContext, VERIFIER, new SecureRandom());
        this.sslFactory = sslContext.getSocketFactory();
        this.sslConnFactory = new SSLConnectionSocketFactory(sslContext, VERIFIER);
        this.sslIOSessionStrategy = new SSLIOSessionStrategy(sslContext, VERIFIER);
    }

    // @SneakyThrows
    // public synchronized SSLSocketFactory getSSLSocketFactory(SSLVersion version) {
    //     if (sslFactory != null) {
    //         return sslFactory;
    //     }
    //     SSLContext context = getSSLContext(version);
    //     initSSLContext(context, VERIFIER, null);
    //     sslFactory = context.getSocketFactory();
    //     return sslFactory;
    // }
    //
    // @SneakyThrows
    // public synchronized SSLConnectionSocketFactory getSSLConnSocketFactory(SSLVersion version) {
    //     if (sslConnFactory != null) {
    //         return sslConnFactory;
    //     }
    //     SSLContext context = getSSLContext(version);
    //     initSSLContext(context, VERIFIER, new SecureRandom());
    //     sslConnFactory = new SSLConnectionSocketFactory(context, VERIFIER);
    //     return sslConnFactory;
    // }
    //
    // @SneakyThrows
    // public synchronized SSLIOSessionStrategy getSSLIOSessionStrategy(SSLVersion version) {
    //     if (sslIOSessionStrategy != null) {
    //         return sslIOSessionStrategy;
    //     }
    //     SSLContext context = getSSLContext(version);
    //     initSSLContext(context, VERIFIER, new SecureRandom());
    //     sslIOSessionStrategy = new SSLIOSessionStrategy(context, VERIFIER);
    //     return sslIOSessionStrategy;
    // }

    // -------------------------- 私有方法 --------------------------

    /**
     * TODO 自定义证书
     *
     * @param keyStorePath
     * @param keyStorepass
     */
    @SneakyThrows
    private SSLContext getSSLContextWithKey(String keyStorePath, String keyStorepass) {
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore trustStore = KeyStore.getInstance(keyStoreType);
        File keyStore = new File(keyStorePath);
        try (FileInputStream in = new FileInputStream(keyStore);) {
            trustStore.load(in, keyStorepass.toCharArray());
            TrustSelfSignedStrategy strategy = new TrustSelfSignedStrategy();
            // 相信自己的CA和所有自签名的证书
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, strategy).build();
            return sslContext;
        }
    }

    @SneakyThrows
    private SSLContext getSSLContext(SSLVersion version) {
        return SSLContext.getInstance(version.get());
    }

    @SneakyThrows
    private void initSSLContext(SSLContext sslContext, X509TrustManager verifier, SecureRandom secureRandom) {
        sslContext.init(null, new TrustManager[]{verifier}, secureRandom);
    }

    // -------------------------- 成员方法 --------------------------

    public SSLSocketFactory getSSLSocketFactory() {
        return sslFactory;
    }
    public SSLConnectionSocketFactory getSSLConnSocketFactory() {
        return sslConnFactory;
    }
    public SSLIOSessionStrategy getSSLIOSessionStrategy() {
        return sslIOSessionStrategy;
    }

}
