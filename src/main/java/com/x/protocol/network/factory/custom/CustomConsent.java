package com.x.protocol.network.factory.custom;

import com.ax.protocol.network.factory.custom.data.ConcentInfo;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 10:30
 */
public abstract class CustomConsent {
    protected final String name;

    protected final boolean keepAlive;

    protected final boolean autoDetectData;

    protected final boolean errClose;

    private CustomServiceConsent svcConsent;

    public CustomConsent(String name, boolean keepAlive, boolean autoDetectData, boolean errClose) {
        this.name = name;
        this.keepAlive = keepAlive;
        this.autoDetectData = autoDetectData;
        this.errClose = errClose;
    }

    public void close() {
        if (svcConsent != null) {
            svcConsent.close();
        } else {
            try {
                this.onClose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isClosed() {
        return svcConsent != null && svcConsent.isClosed();
    }

    public void notifyDataAvailable() {
        if (svcConsent != null) {
            svcConsent.notifyData(svcConsent.getNetworkIO());
        }
    }

    void setServiceConsent(CustomServiceConsent serviceConsent) {
        this.svcConsent = serviceConsent;
    }

    public abstract ConcentInfo getConsentInfo();

    public abstract int available() throws Exception;

    public abstract <T> T read() throws Exception;

    public abstract void reset() throws Exception;

    public abstract <T> boolean send(T data) throws Exception;

    public abstract void flush() throws Exception;

    public abstract void onClose();
}
