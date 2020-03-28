package com.x.protocol.network.factory.custom;

import com.ax.protocol.network.factory.custom.data.ConcentInfo;
import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkIO;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 10:57
 */
public class CustomServiceConsent extends NetworkConsent {

    private final CustomConsent consent;

    private final CustomInput in;

    private final CustomOutput out;

    public CustomServiceConsent(CustomService service,
                                NetworkConsentType type,
                                CustomConsent custom) {
        super(custom.name, service, type, custom.keepAlive, custom.autoDetectData);
        custom.setServiceConsent(this);
        this.consent = custom;
        this.in = new CustomInput(service, this, custom);
        this.out = new CustomOutput(service, this, custom);
        ConcentInfo info = custom.getConsentInfo();
        this.localHost = info.getConcentLocalHost();
        this.localPort = info.getConcentLocalPort();
        this.remoteHost = info.getConcentRemoteHost();
        this.remotePort = info.getConcentRemotePort();
    }

    @Override
    public NetworkIO getNetworkIO() {
        return new NetworkIO(this.service, this, in, out);
    }

    @Override
    protected boolean checkDataAvailable() {
        return this.in.available() > 0;
    }

    @Override
    protected Object getConsentInfo(String consentType) {
        switch (consentType) {
            case CustomKey.INFO_C_LOCAL_HOST:
                return this.localHost;
            case CustomKey.INFO_C_LOCAL_PORT:
                return this.localPort;
            case CustomKey.INFO_C_REMOTE_HOST:
                return this.remoteHost;
            case CustomKey.INFO_C_REMOTE_PORT:
                return this.remotePort;
            default:
                return super.getInformation(consentType);
        }
    }

    @Override
    protected void closeConsent() {
        try {
            this.consent.onClose();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
