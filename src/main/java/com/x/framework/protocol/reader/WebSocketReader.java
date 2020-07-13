package com.x.framework.protocol.reader;

import com.x.commons.util.convert.Converts;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IProtocol;
import com.x.protocol.core.IProtocolReader;
import com.x.protocol.network.factory.http.websocket.IWebSocketInput;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkIO;

/**
 * @Desc
 * @Date 2020-03-21 23:34
 * @Author AD
 */
public class WebSocketReader implements IProtocolReader {
    
    private static final int MAX_SIZE = 1 << 20;
    
    private ChannelData channelData;
    @Override
    public void init(IProtocol prtc, ChannelInfo info, INetworkIO io) throws Exception {
        IWebSocketInput in = (IWebSocketInput) io.getInput();
        String content = in.getText();
        if (Strings.isNotNull(content) &&content.length() <= MAX_SIZE) {
            String[] st = content.split("\\|");
            if (st != null && st.length >= 9) {
                boolean isReq = "0".equals(st[0]);
                String langKey = st[7];
                if (langKey.length() > 0) {
                    INetworkConsent consent = in.getConsent();
                    if (!langKey.equals(consent.getLangKey())) {
                        consent.setLangKey(langKey);
                    }
                }
            
                String data = st[8];
                if (st.length > 9) {
                    int i = 9;
                
                    for (int c = st.length; i < c; ++i) {
                        data = data + "|" + st[i];
                    }
                }
            
                if (isReq) {
                    channelData = ChannelData.fromRemoteRequest(st[1], st[2], st[3], Converts.toInt(st[4]),
                            data, null);
                } else {
                    channelData = ChannelData.fromRemoteResponse(st[1], st[2], st[3], Converts.toInt(st[4]),
                            data, null, Converts.toInt(st[5]), st[6]);
                }
            
            }
        }
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
