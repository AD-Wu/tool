package com.x.protocol.network.factory.http.websocket;

import com.x.commons.enums.Charset;
import com.x.commons.local.Locals;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.string.Strings;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkOutput;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.enums.HttpKey;
import org.apache.catalina.websocket.WsOutbound;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @Desc
 * @Date 2020-03-07 23:35
 * @Author AD
 */
public class WebSocketOutput extends NetworkOutput implements IWebSocketOutput {
    
    private WsOutbound out;
    
    private final String encoding;
    
    public WebSocketOutput(NetworkService service, NetworkConsent consent, WsOutbound out) {
        super(service, consent);
        this.out = out;
        this.encoding = service.getConfig().getString(HttpKey.PARAM_S_CONTENT_ENCODING, Charset.UTF8);
    }
    
    @Override
    public boolean send(String msg) {
        if (super.consent.isClosed() || out == null) {
            return false;
        }
        if (Strings.isNull(msg)) {
            return true;
        } else {
            CharBuffer buf = CharBuffer.wrap(msg.toCharArray());
            try {
                out.writeTextMessage(buf);
                out.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                super.service.notifyError(super.consent, Locals.text("protocol.ws.text.err", e.getMessage()));
                return false;
            }
        }
    }
    
    @Override
    public boolean send(byte[] bytes) {
        if (XArrays.isEmpty(bytes)) {
            return true;
        } else {
            ByteBuffer buf = ByteBuffer.wrap(bytes);
            return this.send(buf);
        }
    }
    
    @Override
    public boolean send(ByteBuffer buffer) {
        if (super.consent.isClosed() || out == null) {
            return false;
        }
        if (buffer == null) {
            return true;
        } else {
            try {
                out.writeBinaryMessage(buffer);
                out.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                super.service.notifyError(super.consent, Locals.text("protocol.ws.byte.err", e.getMessage()));
                return false;
            }
        }
    }
    
    @Override
    public void flush() {
        if (!super.consent.isClosed() && out != null) {
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                super.service.notifyError(super.consent, Locals.text("protocol.ws.flush.err", e.getMessage()));
            }
        }
    }
    
    void close() {
        if (out != null) {
            int status = 1000;
            String msg = Locals.text("protocol.ws.close");
            ByteBuffer data = Converts.toByteBuffer(msg, Charset.UTF8);
            try {
                out.close(status, data);
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
                super.service.notifyError(super.consent, Locals.text("protocol.ws.close.err", e.getMessage()));
            }
        }
    }
    
}
