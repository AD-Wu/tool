package com.x.protocol.network.factory.http.websocketclient;

import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkInput;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.websocket.IWebSocketInput;

import java.nio.ByteBuffer;

/**
 * @Desc
 * @Date 2020-03-08 00:51
 * @Author AD
 */
public class WebSocketClientInput extends NetworkInput implements IWebSocketInput {
    
    private byte[] data;
    
    private String text;
    
    public WebSocketClientInput(NetworkService service, NetworkConsent consent, ByteBuffer buffer, String text) {
        super(service, consent);
        this.data = buffer == null ? null : buffer.array();
        this.text = text;
    }
    
    @Override
    public <T> T getProtocolReader() {
        return (T)((WebSocketClientConsent)super.consent).getReader();
    }
    
    @Override
    public void setProtocolReader(Object protocolReader) {
        ((WebSocketClientConsent)super.consent).setReader(protocolReader);
    }
    
    @Override
    public byte[] getBytes() {
        return this.data;
    }
    
    @Override
    public String getText() {
        return this.text;
    }
    
}
