package com.x.protocol.network.factory.custom;

import com.x.commons.local.Locals;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkInput;
import com.x.protocol.network.factory.NetworkService;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 10:37
 */
public class CustomInput extends NetworkInput implements ICustomInput {
    private CustomConsent custom;

    private final Object lock = new Object();

    public CustomInput(NetworkService service, NetworkConsent consent, CustomConsent custom) {
        super(service, consent);
        this.custom = custom;
    }

    @Override
    public int available() {
        try {
            synchronized (lock) {
                return this.custom.available();
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.service.notifyError(super.consent, Locals.text("protocol.custom.available",
                                                                 e.getMessage()));
            return 0;
        }

    }

    @Override
    public <T> T read() {
        try {
            synchronized (lock) {
                return this.custom.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
            service.notifyError(consent, Locals.text("protocol.custom.read", e.getMessage()));
            if (custom.errClose) {
                super.consent.close();
            }
            return null;
        }
    }

    @Override
    public void reset() {
        try {
            synchronized (lock) {
                this.custom.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.service.notifyError(super.consent, Locals.text("protocol.custom.reset",
                                                                 e.getMessage()));
            if (custom.errClose) {
                super.consent.close();
            }
        }
    }
}
