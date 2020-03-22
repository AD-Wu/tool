package com.x.framework.protocol.sender;

import com.x.commons.enums.Charset;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IProtocol;
import com.x.protocol.core.IProtocolSender;
import com.x.protocol.network.factory.http.webclient.IWebClientHandler;
import com.x.protocol.network.factory.http.webclient.IWebClientOutput;
import com.x.protocol.network.interfaces.INetworkIO;

/**
 * @Desc
 * @Date 2020-03-21 23:01
 * @Author AD
 */
public class WebClientSender implements IProtocolSender {
    
    @Override
    public boolean send(IProtocol prtc, ChannelInfo info, INetworkIO io, ChannelData data) throws Exception {
        IWebClientOutput out = (IWebClientOutput) io.getOutput();
        out.setRequestData(data);
        String template = "{0}?cmd={1}&ctrl={2}&version={3}";
        String url = Strings.replace(template, out.getDefaultUploadURL(), data.getCommand(), data.getControl(),
                data.getVersion());
        String sdata = Strings.of(data.getDataSerialized());
        byte[] bs = Strings.isNull(sdata) ? XArrays.EMPTY_BYTE : sdata.getBytes(Charset.UTF8);
        IWebClientHandler handler = out.sendRequest(url, bs);
        return handler != null;
    }
    
}
