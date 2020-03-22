package com.x.framework.protocol.sender;

import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IProtocol;
import com.x.protocol.core.IProtocolSender;
import com.x.protocol.network.factory.http.websocket.IWebSocketOutput;
import com.x.protocol.network.interfaces.INetworkIO;

/**
 * @Desc
 * @Date 2020-03-21 23:14
 * @Author AD
 */
public class WebSocketSender implements IProtocolSender {
    
    @Override
    public boolean send(IProtocol prtc, ChannelInfo info, INetworkIO io, ChannelData data) throws Exception {
        SB sb = New.sb();
        sb.append(data.isRequest() ? "0|" : "1|");
        sb.append(data.getCommand());
        sb.append("|");
        sb.append(data.getControl());
        sb.append("|");
        sb.append(data.getVersion());
        sb.append("|");
        sb.append(data.getMappingIndex());
        sb.append("|");
        sb.append(data.getStatus());
        sb.append("|");
        sb.append(data.getMessage());
        sb.append("|");
        sb.append(info.getConsent().getLangKey());
        sb.append("|");
        if (data.getDataSerialized() != null) {
            sb.append(data.getDataSerialized());
        }
    
        IWebSocketOutput out = (IWebSocketOutput) io.getOutput();
        return out.send(sb.toString());
    }
    
}
