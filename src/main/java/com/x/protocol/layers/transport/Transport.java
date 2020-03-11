package com.x.protocol.layers.transport;

import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IChannel;
import com.x.protocol.enums.ProtocolStatus;
import com.x.protocol.enums.ResponseMode;
import com.x.protocol.layers.Protocol;
import com.x.protocol.layers.transport.interfaces.ITransportResponse;

/**
 * @Desc TODO
 * @Date 2020-03-10 00:37
 * @Author AD
 */
public class Transport extends TransportResponser {

    public Transport(Protocol protocol) {
        super(protocol);
    }

    public boolean send(ChannelInfo info, ChannelData data, ITransportResponse resp,
                        long timeout, ResponseMode mode, boolean callbackOnError) {
        if(info.getConsent().isClosed()){
            if(callbackOnError){
                super.callbackErrorResponse(resp, info, data, ProtocolStatus.CONNECTION_CLOSED);
            }
            return false;
        }else if(super.containsCallbackInfo(info,data)){
            super.callbackErrorResponse(resp,info,data,ProtocolStatus.WAITING_FOR_RESPONSE);
            return false;
        }else{
            IChannel channel = info.getChannel();
            boolean send = channel.send(info, data);
            info.resetNetworkIO();
            if(send){
                if(data.isRequest()){
                    super.addTimeout(info,data,resp,timeout,mode);
                }
                return true;
            }else{
                if(callbackOnError){
                    super.callbackErrorResponse(resp,info,data,ProtocolStatus.METHOD_FAILURE);
                }
                return false;
            }
        }
    }

    public void checkTransportTimeout(long now){
        super.checkCallbackTimeout(now);
        if(!super.protocol.isStopped()){
            super.checkClientConnections(now);
        }
    }
}
