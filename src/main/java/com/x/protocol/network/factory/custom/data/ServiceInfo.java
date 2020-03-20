package com.x.protocol.network.factory.custom.data;

import com.x.commons.util.string.Strings;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 10:25
 */
public final class ServiceInfo {
    private final String serviceLocalHost;

    private final int serviceLocalPort;

    public ServiceInfo(String serviceLocalHost, int serviceLocalPort) {
        this.serviceLocalHost = serviceLocalHost;
        this.serviceLocalPort = serviceLocalPort;
    }

    public String getServiceLocalHost() {
        return serviceLocalHost;
    }

    public int getServiceLocalPort() {
        return serviceLocalPort;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
}
