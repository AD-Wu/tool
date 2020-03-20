package com.x.protocol.network.factory.custom;

import com.x.commons.local.Locals;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkOutput;
import com.x.protocol.network.factory.NetworkService;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 11:05
 */
public class CustomOutput extends NetworkOutput implements ICustomOutput {
    private CustomConsent custom;

    private final Object lock = new Object();

    public CustomOutput(NetworkService service, NetworkConsent consent, CustomConsent custom) {
        super(service, consent);
        this.custom = custom;

    }

    @Override
    public <T> boolean send(T t) {
        if (t == null) {
            return true;
        } else {
            try {
                synchronized (lock) {
                    return this.custom.send(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.service.notifyError(super.consent, Locals.text("protocol.custom.send",
                                                                     e.getMessage()));
                if (custom.errClose) {
                    super.consent.close();
                }
                return false;
            }
        }
    }

    @Override
    public void flush() {
        try {
            synchronized (lock) {
                this.custom.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.service.notifyError(super.consent, Locals.text("protocol.custom.flush",
                                                                 e.getMessage()));
            if (custom.errClose) {
                super.consent.close();
            }
        }
    }
}
