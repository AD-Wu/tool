package com.x.protocol.network.factory.custom;

import com.x.commons.collection.DataSet;
import com.x.commons.local.Locals;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.string.Strings;
import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.custom.data.ServiceInfo;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkNotification;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 10:59
 */
public class CustomService extends NetworkService {
    private ICustomService service;

    private ServiceInfo svcInfo;

    private String svcInfoText;

    public CustomService(INetworkNotification notification) {
        super(notification, false);

    }

    @Override
    protected Object getServiceInfo(String serviceProperty) {
        switch (serviceProperty) {
            case CustomKey.INFO_S_LOCAL_HOST:
                return svcInfo.getServiceLocalHost();
            case CustomKey.INFO_S_LOCAL_PORT:
                return svcInfo.getServiceLocalPort();
            default:
                return super.config.get(serviceProperty);
        }
    }


    @Override
    public String getServiceInfo() {
        return Strings.isNull(svcInfoText) ? "CUSTOM" : svcInfoText;
    }

    @Override
    protected void serviceStart() {
        if (super.isServerMode()) {
            super.notifyServiceStart();
        } else {
            String svcClass = config.getString(CustomKey.PARAM_S_SERVICE_CLASS);
            try {
                this.service = Clazzs.newInstance(svcClass, ICustomService.class);
            } catch (Exception e) {
                this.service = null;
                e.printStackTrace();
                super.notifyError(null, Locals.text("protocol.custom.start", e.getMessage()));
                super.stop();
            }

            ICustomNotification notification = new ICustomNotification() {
                @Override
                public void notifyServiceStart(ServiceInfo info) {
                    CustomService.this.svcInfo = info;
                    CustomService.this.svcInfoText = Strings.replace("HOST:\"{0}:{1}\"",
                                                                     info.getServiceLocalHost(),
                                                                     info.getServiceLocalPort());
                    CustomService.this.notifyServiceStart();
                }

                @Override
                public boolean notifyRemoteStart(CustomConsent consent) {
                    if (consent == null) {
                        return false;
                    } else {
                        CustomServiceConsent svcConsent = new CustomServiceConsent(
                                CustomService.this,
                                NetworkConsentType.REMOTE_TO_LOCAL, consent);
                        return svcConsent.start();
                    }
                }

                @Override
                public boolean notifyClientStart(CustomConsent consent) {
                    if (consent == null) return false;
                    CustomServiceConsent clientConsent = new CustomServiceConsent(
                            CustomService.this,
                            NetworkConsentType.LOCAL_TO_REMOTE, consent);
                    return clientConsent.start();
                }
            };

            try {
                if (this.service.onStart(this.config, notification)) {
                    super.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.stop();
                super.notifyError(null, Locals.text("protocol.custom.start.err", svcClass,
                                                    e.getMessage()));
            }
        }
    }

    @Override
    protected void serviceStop() {
        if (this.service != null && super.isServerMode()) {
            try {
                this.service.onStop();
            } catch (Exception e) {
                e.printStackTrace();
                super.notifyError(null, Locals.text("protocol.custom.stop.err",
                                                    this.service.getClass().getName(),
                                                    e.getMessage()));
            }
        }
    }

    @Override
    public INetworkConsent connect(String name, DataSet data) throws Exception {
        String clientClass = super.config.getString(CustomKey.PARAM_S_CLIENT_CLASS);
        CustomConsent consent = null;
        CustomServiceConsent svcConsent = null;
        try {
            ICustomClient client = Clazzs.newInstance(clientClass, ICustomClient.class);
            consent = client.connect(name, data);
            svcConsent = new CustomServiceConsent(this, NetworkConsentType.LOCAL_TO_REMOTE,
                                                  consent);
            return svcConsent.start() ? svcConsent : null;
        } catch (Exception e) {
            e.printStackTrace();
            if (svcConsent != null) {
                svcConsent.close();
            }
            if (consent != null) {
                consent.close();
            }
            throw new Exception(Locals.text("protocol.custom.client", clientClass, e.getMessage()));
        }

    }
}
