package com.x.protocol.network.factory.http.websocket;

import com.x.commons.util.collection.XArrays;
import com.x.protocol.network.factory.NetworkInput;
import com.x.protocol.network.factory.NetworkService;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @Desc TODO
 * @Date 2020-03-07 20:53
 * @Author AD
 */
public class WebSocketInput extends NetworkInput implements IWebSocketInput {
    
    private String cb;
    
    private byte[] bb;
    
    public WebSocketInput(NetworkService service, WebSocketConsent consent, CharBuffer cb, ByteBuffer bb) {
        super(service, consent);
        this.cb = cb == null ? null : cb.toString();
        this.bb = bb == null ? XArrays.EMPTY_BYTE : bb.array();
    }
    
    @Override
    public <T> T getProtocolReader() {
        return (T)((WebSocketConsent)super.consent).getReader();
    }
    
    @Override
    public void setProtocolReader(Object protocolReader) {
        super.setProtocolReader(protocolReader);
    }
    
    @Override
    public byte[] getBytes() {
        return bb;
    }
    
    @Override
    public String getText() {
        return cb;
    }
    
}
