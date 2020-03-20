package com.x.protocol.layers.transport.channel;

import com.x.commons.util.convert.Converts;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.layers.Protocol;
import com.x.protocol.layers.transport.Transport;
import com.x.protocol.layers.transport.channel.core.Channel;
import com.x.protocol.network.factory.serial.SerialKey;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkIO;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 13:41
 */
public class SerialChannel extends Channel {
    
    public SerialChannel(Transport transport, Protocol protocol) {
        super(transport, protocol);
    }
    
    @Override
    protected ChannelInfo getChannelInfo(INetworkConsent consent, INetworkIO io) {
        ChannelInfo info = new ChannelInfo(this, consent, io);
        info.setLocalHost("localhost");
        info.setLocalPort(Converts.toInt(consent.getInformation(SerialKey.INFO_C_TYPE)));
        info.setRemoteHost(Strings.of(consent.getInformation(SerialKey.INFO_C_NAME)));
        info.setRemotePort(Converts.toInt(consent.getInformation(SerialKey.INFO_C_TYPE)));
        return info;
    }
    
}
