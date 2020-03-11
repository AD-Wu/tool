package com.x.protocol.core;

import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.string.Strings;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkIO;

/**
 * @Desc
 * @Date 2020-03-08 22:43
 * @Author AD
 */
public class ChannelInfo {
    
    // ------------------------ 变量定义 ------------------------
    private final IChannel channel;
    
    private final INetworkConsent consent;
    
    private INetworkIO networkIO;
    
    private String localHost;
    
    private int localPort;
    
    private String remoteHost;
    
    private int remotePort;
    
    private String info;
    
    private final String channelInfoKey;
    
    // ------------------------ 构造方法 ------------------------
    public ChannelInfo(IChannel channel, INetworkConsent consent, INetworkIO networkIO) {
        this.channel = channel;
        this.consent = consent;
        this.networkIO = networkIO;
        this.channelInfoKey = consent.getConsentIndex() + "|" + channel.getName();
    }
    // ------------------------ 方法定义 ------------------------
    
    public String text(String localKey, Object... msg) {
        String langKey = consent.getLangKey();
        return Strings.isNull(langKey) ?
                Locals.text(localKey, msg) :
                Locals.getLocal(langKey).text(localKey, msg);
    }
    
    @Override
    public String toString() {
        if (Strings.isNull(info)) {
            SB sb = New.sb();
            sb.append(consent.getConnectionInfo());
            sb.append("-[IP:");
            sb.append(remoteHost);
            sb.append(":");
            sb.append(remotePort);
            sb.append("]");
            this.info = sb.toString();
        }
        return this.info;
    }
    
    public void resetNetworkIO() {
        this.networkIO = null;
    }
    
    public IChannel getChannel() {
        return channel;
    }
    
    public INetworkConsent getConsent() {
        return consent;
    }
    
    public INetworkIO getNetworkIO() {
        return networkIO;
    }
    
    public String getChannelInfoKey() {
        return channelInfoKey;
    }
    
    public String getLocalHost() {
        return localHost;
    }
    
    public void setLocalHost(String localHost) {
        this.localHost = localHost;
    }
    
    public int getLocalPort() {
        return localPort;
    }
    
    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }
    
    public String getRemoteHost() {
        return remoteHost;
    }
    
    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }
    
    public int getRemotePort() {
        return remotePort;
    }
    
    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }
    
    public String getInfo() {
        return info;
    }
    
    public void setInfo(String info) {
        this.info = info;
    }
    
}
