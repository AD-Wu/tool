package com.x.protocol.network.factory.custom.data;

import com.x.commons.util.string.Strings;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 10:23
 */
public final class ConsentInfo {
    private final String consentLocalHost;

    private final int consentLocalPort;

    private final String consentRemoteHost;

    private final int consentRemotePort;

    public ConsentInfo(String consentLocalHost, int consentLocalPort, String consentRemoteHost, int consentRemotePort) {
        this.consentLocalHost = consentLocalHost;
        this.consentLocalPort = consentLocalPort;
        this.consentRemoteHost = consentRemoteHost;
        this.consentRemotePort = consentRemotePort;
    }

    public String getConsentLocalHost() {
        return consentLocalHost;
    }

    public int getConsentLocalPort() {
        return consentLocalPort;
    }

    public String getConsentRemoteHost() {
        return consentRemoteHost;
    }

    public int getConsentRemotePort() {
        return consentRemotePort;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
}
