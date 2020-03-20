package com.x.protocol.network.factory.custom;

import com.x.protocol.network.factory.custom.data.ServiceInfo;

public interface ICustomNotification {
    void notifyServiceStart(ServiceInfo info);

    boolean notifyRemoteStart(CustomConsent consent);

    boolean notifyClientStart(CustomConsent consent);
}
