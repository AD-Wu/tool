package com.x.protocol.network.factory.socket;

import com.x.commons.local.Locals;
import com.x.commons.util.collection.XArrays;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkInput;
import com.x.protocol.network.factory.NetworkService;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Desc TODO
 * @Date 2020-03-02 00:14
 * @Author AD
 */
public final class SocketInput extends NetworkInput implements ISocketInput {
    
    private InputStream in;
    
    private Object inLock = new Object();
    
    public SocketInput(NetworkService service, NetworkConsent consent, InputStream in) {
        super(service, consent);
        this.in = in;
    }
    
    @Override
    public int available() {
        synchronized (inLock) {
            try {
                return this.in == null ? 0 : this.in.available();
            } catch (IOException e) {
                e.printStackTrace();
                super.service.notifyError(super.consent, Locals.text("protocol.socket.available", e.getMessage()));
                super.consent.close();
                return 0;
            }
        }
    }
    
    @Override
    public int read() {
        synchronized (inLock) {
            try {
                return in.read();
            } catch (IOException e) {
                e.printStackTrace();
                super.service.notifyError(super.consent, Locals.text("protocol.socket.read.byte", e.getMessage()));
                super.consent.close();
                return -1;
            }
        }
    }
    
    @Override
    public int read(byte[] buffer) {
        if (!XArrays.isEmpty(buffer)) {
            synchronized (inLock) {
                try {
                    this.in.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                    super.service.notifyError(super.consent, Locals.text("protocol.socket.read.bytes", e.getMessage()));
                    super.consent.close();
                    return -1;
                }
            }
        }
        return -1;
    }
    
    @Override
    public void reset() {
        try {
            synchronized (inLock) {
                if (this.in == null) {
                    return;
                }
                in.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
            super.service.notifyError(super.consent, Locals.text("protocol.socket.reset", e.getMessage()));
            super.consent.close();
        }
    }
    
    void close() {
        try {
            synchronized (inLock) {
                if (this.in != null) {
                    in.close();
                    this.in = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
