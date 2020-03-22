package com.x.framework.protocol.reader;

import com.ax.commons.utils.ConvertHelper;
import com.x.commons.enums.Charset;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IProtocol;
import com.x.protocol.core.IProtocolReader;
import com.x.protocol.enums.ProtocolStatus;
import com.x.protocol.network.factory.http.webclient.IWebClientInput;
import com.x.protocol.network.interfaces.INetworkIO;
import org.apache.http.HttpStatus;

/**
 * @Desc
 * @Date 2020-03-21 23:20
 * @Author AD
 */
public class WebClientReader implements IProtocolReader {
    
    private ChannelData channelData;
    
    @Override
    public void init(IProtocol prtc, ChannelInfo info, INetworkIO io) throws Exception {
        IWebClientInput in = io.getInput();
        ChannelData reqData = (ChannelData) in.getRequestData();
        if (reqData != null) {
            String cmd = reqData.getCommand();
            String ctrl = reqData.getControl();
            String version = reqData.getVersion();
            String data = null;
            int status = in.getStatusCode();
            String message = in.getStatusMessage();
            if (status == HttpStatus.SC_OK) {
                status = ProtocolStatus.OK;
                data = new String(in.getResponseStreamData(), Charset.UTF8);
                if (data != null) {
                    String[] ary = data.split("|");
                    int len = ary.length;
                    if (len >= 3) {
                        status = ConvertHelper.toInt(ary[0]);
                        message = ary[1];
                        if (len == 3) {
                            data = ary[2];
                        } else {
                            SB sb = New.sb();
                            
                            for (int i = 2; i < len; ++i) {
                                if (i > 2) {
                                    sb.append("|");
                                }
                                sb.append(ary[i]);
                            }
                            data = sb.toString();
                        }
                    }
                }
            } else {
                status = ProtocolStatus.OTHERS;
                message = in.getStatusMessage();
            }
            this.channelData = ChannelData.fromRemoteResponse(cmd, ctrl, version, 0, data, null, status, message);
        }
    }
    
    @Override
    public boolean nextPackage() throws Exception {
        return this.channelData != null;
    }
    
    @Override
    public ChannelData outputData() throws Exception {
        return this.channelData;
    }
    
    @Override
    public void reset() {
        this.channelData = null;
    }
    
}
