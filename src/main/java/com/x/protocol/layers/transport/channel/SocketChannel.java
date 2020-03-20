package com.x.protocol.layers.transport.channel;

import com.x.protocol.core.ChannelInfo;
import com.x.protocol.layers.Protocol;
import com.x.protocol.layers.transport.Transport;
import com.x.protocol.layers.transport.channel.core.Channel;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkIO;

/**
 * @Desc
 * @Date 2020-03-10 00:24
 * @Author AD
 */
public class SocketChannel extends Channel {
    
    public SocketChannel(Transport transport, Protocol protocol) {
        super(transport, protocol);
    }
    
    @Override
    protected ChannelInfo getChannelInfo(INetworkConsent consent, INetworkIO io) {
        ChannelInfo info = new ChannelInfo(this, consent, io);
        super.setChannelInfo(consent, info);
        return info;
    }
    
}
