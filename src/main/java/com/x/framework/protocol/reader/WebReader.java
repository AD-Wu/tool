package com.x.framework.protocol.reader;

import com.x.commons.enums.Charset;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IProtocol;
import com.x.protocol.core.IProtocolReader;
import com.x.protocol.network.factory.http.web.IWebInput;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkIO;

/**
 * @Desc
 * @Date 2020-03-21 23:28
 * @Author AD
 */
public class WebReader implements IProtocolReader {
    
    private static final int MAX_SIZE = 1 << 20;
    
    private ChannelData channelData;
    
    @Override
    public void init(IProtocol prtc, ChannelInfo info, INetworkIO io) throws Exception {
        IWebInput in = (IWebInput) io.getInput();
        String langKey = in.getParameter("lang");
        if (Strings.isNotNull(langKey)) {
            INetworkConsent consent = in.getConsent();
            if (!langKey.equals(consent.getLangKey())) {
                consent.setLangKey(langKey);
            }
        }
    
        String cmd = in.getParameter("cmd");
        String ctrl = in.getParameter("ctrl");
        String version = in.getParameter("version");
        String data = new String(in.getInputStreamData(MAX_SIZE), Charset.UTF8);
        channelData = ChannelData.fromRemoteRequest(cmd, ctrl, version, 0, data, null);
    }
    
    @Override
    public boolean nextPackage() throws Exception {
        return channelData!=null;
    }
    
    @Override
    public ChannelData outputData() throws Exception {
        return channelData;
    }
    
    @Override
    public void reset() {
        channelData=null;
    }
    
}
