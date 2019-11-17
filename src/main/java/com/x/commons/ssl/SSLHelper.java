package com.x.commons.ssl;

import com.arronlong.httpclientutil.common.SSLs;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.x.commons.ssl.enums.SSLVersion;
import lombok.SneakyThrows;
import lombok.Synchronized;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Optional;

/**
 * @Desc TODO
 * @Date 2019-11-17 21:04
 * @Author AD
 */
public class SSLHelper {

    private static final SSLVerifier VERIFIER = new SSLVerifier();

    private static SSLSocketFactory sslFactory;

    private static SSLConnectionSocketFactory sslConnFactory;

    private static SSLIOSessionStrategy sslIOSessionStrategy;

    private SSLContext sslContext;

    @SneakyThrows
    @Synchronized
    public SSLSocketFactory getSSLSocketFactory(SSLVersion version) {
        if (sslFactory != null) {
            return sslFactory;
        }
        SSLContext context = getSSLContext(version);
        initSSLContext(context, VERIFIER, null);
        sslFactory = context.getSocketFactory();
        return sslFactory;
    }

    @SneakyThrows
    @Synchronized
    public SSLConnectionSocketFactory getSSLConnFactory(SSLVersion version) {
        if (sslConnFactory != null) {
            return sslConnFactory;
        }
        SSLContext context = getSSLContext(version);
        initSSLContext(context, VERIFIER, new SecureRandom());
        sslConnFactory = new SSLConnectionSocketFactory(context, VERIFIER);
        return sslConnFactory;
    }

    @SneakyThrows
    @Synchronized
    public SSLIOSessionStrategy getSSLIOSessionStrategy(SSLVersion version) {
        if (sslIOSessionStrategy != null) {
            return sslIOSessionStrategy;
        }
        SSLContext context = getSSLContext(version);
        initSSLContext(context, VERIFIER, new SecureRandom());
        sslIOSessionStrategy = new SSLIOSessionStrategy(context, VERIFIER);
        return sslIOSessionStrategy;
    }

    @SneakyThrows
    public void customSSL(String keyStorePath, String keyStorepass) {
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore trustStore = KeyStore.getInstance(keyStoreType);
        File keyStore = new File(keyStorePath);
        try (FileInputStream in = new FileInputStream(keyStore);) {
            trustStore.load(in, keyStorepass.toCharArray());
            TrustSelfSignedStrategy strategy = new TrustSelfSignedStrategy();
            // 相信自己的CA和所有自签名的证书
            sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, strategy).build();
        }
    }

    @SneakyThrows
    private SSLContext getSSLContext(SSLVersion version) {
        sslContext = Optional.ofNullable(sslContext).orElse(SSLContext.getInstance(version.get()));
        return sslContext;
    }

    @SneakyThrows
    private void initSSLContext(SSLContext context, X509TrustManager verifier, SecureRandom secureRandom) {
        context.init(null, new TrustManager[]{VERIFIER}, secureRandom);
    }

}
