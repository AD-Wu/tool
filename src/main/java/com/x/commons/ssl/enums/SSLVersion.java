package com.x.commons.ssl.enums;

/**
 * @Desc TODO
 * @Date 2019-11-17 21:09
 * @Author AD
 */
public enum SSLVersion {

    SSL("SSL"),
    SSLv3("SSLv3"),
    TLSv1("TLSv1"),
    TLSv1_1("TLSv1.1"),
    TLSv1_2("TLSv1.2");

    public String get() {
        return this.version;
    }

    private final String version;

    private SSLVersion(String version) {
        this.version = version;
    }

}
