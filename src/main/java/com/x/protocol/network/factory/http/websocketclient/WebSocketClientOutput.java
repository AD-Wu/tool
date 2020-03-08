package com.x.protocol.network.factory.http.websocketclient;

import com.x.commons.collection.DataSet;
import com.x.commons.local.Locals;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkOutput;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.websocket.IWebSocketOutput;

import javax.websocket.Session;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @Desc TODO
 * @Date 2020-03-08 00:52
 * @Author AD
 */
public final class WebSocketClientOutput extends NetworkOutput implements IWebSocketOutput {
    
    private Session session;
    
    private final Object outLock = new Object();
    
    public WebSocketClientOutput(NetworkService service, NetworkConsent consent, Session session, DataSet dataSet) {
        super(service, consent);
        this.session = session;
    }
    
    @Override
    public boolean send(String msg) {
        if (super.consent.isClosed()) {
            return false;
        }
        if (!Strings.isNull(msg)) {
            try {
                synchronized (outLock) {
                    if (session == null) {
                        return false;
                    }
                    session.getBasicRemote().sendText(msg);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                super.service.notifyError(super.consent, Locals.text("protocol.wsc.text.err", e.getMessage()));
                super.consent.close();
                return false;
            }
        } else {
            return true;
        }
    }
    
    @Override
    public boolean send(byte[] bytes) {
        if (XArrays.isEmpty(bytes)) {
            return true;
        }
        return send(ByteBuffer.wrap(bytes));
    }
    
    @Override
    public boolean send(ByteBuffer buffer) {
        if (super.consent.isClosed()) {
            return false;
        } else if (buffer == null) {
            return true;
        } else {
            try {
                synchronized (outLock) {
                    if (session == null) {
                        return false;
                    }
                    session.getBasicRemote().sendBinary(buffer);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                super.service.notifyError(super.consent, Locals.text("protocol.wsc.byte.err", e.getMessage()));
                super.consent.close();
                return false;
            }
        }
    }
    
    @Override
    public void flush() {
        if (!super.consent.isClosed()) {
            try {
                synchronized (outLock) {
                    if (session == null) {
                        return;
                    }
                    session.getBasicRemote().flushBatch();
                }
            } catch (IOException e) {
                e.printStackTrace();
                super.service.notifyError(super.consent, Locals.text("protocol.wsc.flush.err", e.getMessage()));
                super.consent.close();
                
            }
        }
    }
    
    void close() {
        if (session != null) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
                super.service.notifyError(consent, Locals.text("protocol.wsc.close.err", e.getMessage()));
            }
            session = null;
        }
    }
    
}
