package com.x.protocol.network.factory.serial;

import com.x.commons.local.Locals;
import com.x.commons.util.collection.XArrays;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkOutput;
import com.x.protocol.network.factory.NetworkService;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Desc
 * @Date 2020-03-19 22:59
 * @Author AD
 */
public class SerialOutput extends NetworkOutput implements ISerialOutput {
    
    private OutputStream out;
    
    private final Object lock = new Object();
    
    public SerialOutput(NetworkService service, NetworkConsent consent, OutputStream out) {
        super(service, consent);
        this.out = out;
    }
    
    @Override
    public boolean send(byte[] bytes) {
        if (XArrays.isEmpty(bytes)) return true;
        try {
            synchronized (lock) {
                if (out == null) {
                    return false;
                }
                out.write(bytes);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            super.service.notifyError(super.consent, Locals.text("protocol.serial.send.byte", e.getMessage()));
            super.consent.close();
            return false;
        }
    }
    
    @Override
    public void flush() {
        
        try {
            synchronized (lock) {
                if (out == null) {
                    return;
                }
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            super.service.notifyError(super.consent, Locals.text("protocol.serial.flush", e.getMessage()));
            super.consent.close();
        }
    }
    
    void close() {
        try {
            synchronized (lock) {
                if (out != null) {
                    out.close();
                    out = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
