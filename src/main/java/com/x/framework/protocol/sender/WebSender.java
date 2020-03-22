package com.x.framework.protocol.sender;

import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IProtocol;
import com.x.protocol.core.IProtocolSender;
import com.x.protocol.network.factory.http.web.IWebOutput;
import com.x.protocol.network.interfaces.INetworkIO;

/**
 * @Desc
 * @Date 2020-03-21 23:11
 * @Author AD
 */
public class WebSender implements IProtocolSender {
    
    @Override
    public boolean send(IProtocol prtc, ChannelInfo info, INetworkIO io, ChannelData data) throws Exception {
        IWebOutput out = (IWebOutput) io.getOutput();
        SB sb = New.sb();
        sb.append(data.getStatus());
        sb.append("|");
        sb.append(data.getMessage());
        sb.append("|");
        if (data.getDataSerialized() != null) {
            sb.append(data.getDataSerialized());
        }
        return out.sendResponse(sb.get());
    }
    
}
