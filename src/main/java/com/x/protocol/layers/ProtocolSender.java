package com.x.protocol.layers;

import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.*;
import com.x.protocol.enums.ProtocolStatus;
import com.x.protocol.enums.ResponseMode;
import com.x.protocol.layers.transport.interfaces.ITransportResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-12 00:38
 * @Author AD
 */
public abstract class ProtocolSender extends ProtocolSerializer {
    
    public ProtocolSender(String name) {
        super(name);
    }
    
    @Override
    public int request(ChannelInfo[] infos, ChannelData data, IDataResponse resp, long index, ResponseMode mode) {
        if (!XArrays.isEmpty(infos) && data != null) {
            DataInfo dataInfo = super.application.getToRemoteResponseConfig(data);
            if (dataInfo == null) {
                super.logger.error(Locals.text("protocol.layer.request", data.getCommandKey(), data.getVersion()));
                return ProtocolStatus.OTHERS;
            } else {
                boolean multiMode = false;
                ISerializer serializer = null;
                Serializable seria = null;
                Serializable dataBean = data.getDataBean();
                Map<ISerializer, Serializable> map = null;
                if (dataBean != null) {
                    multiMode = super.sessions.isMultiSerializer();
                    if (!multiMode && infos.length > 1) {
                        IChannel channel = infos[0].getChannel();
                        for (int i = 1; i < infos.length; i++) {
                            if (infos[i] != channel) {
                                multiMode = true;
                                break;
                            }
                        }
                        if (multiMode) {
                            map = New.map();
                        }
                    }
                    serializer = super.getSerializer(infos[0]);
                    seria = super.serialize(serializer, dataInfo, dataBean);
                }
                int sendCounter = 0;
                ProtocolSender.RequestResponser requestResponser =
                        resp == null ? null : new ProtocolSender.RequestResponser(resp);
                
                for (int i = 0, L = infos.length; i < L; ++i) {
                    ChannelInfo channel = infos[i];
                    Serializable serialized = seria;
                    if (multiMode && i > 0) {
                        ISerializer seriaer = super.getSerializer(channel);
                        if (seriaer != serializer) {
                            if (map != null) {
                                serialized = map.get(seriaer);
                                if (serialized == null) {
                                    serialized = this.serialize(seriaer, dataInfo, dataBean);
                                    map.put(seriaer, serialized);
                                }
                            } else {
                                serialized = this.serialize(seriaer, dataInfo, dataBean);
                            }
                        }
                    }
                    
                    ChannelData reqData = ChannelData.protocolToRemoteRequest(data, channel, dataInfo, serialized);
                    this.callbackDataReady(resp, channel, reqData);
                    if (super.debug && dataInfo.isDebugInfo()) {
                        super.logger.debug(
                                "REQ-TR > " + dataInfo.getDoc() + " - " + reqData.getCommandKey() + ":> " +
                                reqData.getDataSerialized());
                    }
                    
                    if (super.transport.send(channel, reqData, requestResponser, index, mode, true)) {
                        ++sendCounter;
                    }
                }
                
                return sendCounter;
            }
        } else {
            return ProtocolStatus.OTHERS;
        }
    }
    
    @Override
    public boolean requestTry(ChannelInfo[] infos, ChannelData reqData, IDataResponse resp, long index, ResponseMode mode) {
        if (infos != null && infos.length != 0 && reqData != null) {
            DataInfo dataInfo = super.application.getToRemoteRequestConfig(reqData);
            if (dataInfo == null) {
                super.logger.error(Locals.text("protocol.layer.request", reqData.getCommandKey(), reqData.getVersion()));
                return false;
            } else {
                List<ChannelInfo> channels = new ArrayList();
                
                for (int i = 0, L = infos.length; i < L; ++i) {
                    ChannelInfo channel = infos[i];
                    channels.add(channel);
                }
                
                int size = channels.size();
                if (size == 0) {
                    return false;
                } else {
                    boolean multi = false;
                    ISerializer serializer = null;
                    Serializable seria = null;
                    Serializable dataBean = reqData.getDataBean();
                    int i;
                    if (dataBean != null) {
                        multi = this.sessions.isMultiSerializer();
                        if (!multi && size > 1) {
                            IChannel channel = channels.get(0).getChannel();
                            
                            for (i = 1; i < size; ++i) {
                                if (channels.get(i).getChannel() != channel) {
                                    multi = true;
                                    break;
                                }
                            }
                        }
                        
                        serializer = super.getSerializer(channels.get(0));
                        seria = super.serialize(serializer, dataInfo, dataBean);
                    }
                    
                    ProtocolSender.RequestTryResponser reqTryResponser;
                    if (resp == null && size <= 1) {
                        reqTryResponser = null;
                    } else {
                        reqTryResponser = new ProtocolSender.RequestTryResponser(resp, dataInfo, channels, reqData, serializer, seria, multi,
                                index, mode);
                    }
                    
                    i = -1;
                    
                    ChannelInfo channel;
                    ChannelData channelData;
                    do {
                        if (channels.size() <= 0) {
                            return false;
                        }
                        
                        ++i;
                        channel = channels.remove(0);
                        Serializable serializable = seria;
                        if (multi && i > 0) {
                            ISerializer seriaer = this.getSerializer(channel);
                            if (seriaer != serializer) {
                                serializable = this.serialize(seriaer, dataInfo, dataBean);
                            }
                        }
                        
                        channelData = ChannelData.protocolToRemoteRequest(reqData, channel, dataInfo, serializable);
                        this.callbackDataReady(resp, channel, channelData);
                        if (this.debug && dataInfo.isDebugInfo()) {
                            this.logger.debug("REQ-TR > " + dataInfo.getDoc() + " - " + channelData.getCommandKey() + ":> " +
                                              channelData.getDataSerialized());
                        }
                    } while (!this.transport.send(channel, channelData, reqTryResponser, index, mode, channels.size() == 0));
                    
                    return true;
                }
            }
        } else {
            return false;
        }
    }
    
    private ChannelData deserializeChannelData(ChannelInfo info, ChannelData data) {
        DataInfo dataInfo = super.application.getToRemoteResponseConfig(data);
        if (dataInfo == null) {
            data.setStatus(ProtocolStatus.DATA_INVALID);
            data.setMessage(Locals.text("protocol.layer.response.err", data.getCommandKey(),
                    data.getVersion()));
            logger.error(data.getMessage());
        } else {
            Serializable serialized = data.getDataSerialized();
            if (serialized != null) {
                ISerializer serializer = super.getSerializer(info);
                data.setDataBean(super.deserialize(serializer, dataInfo, serialized));
            }
            data.setDataInfo(dataInfo);
        }
        return data;
    }
    
    private void callbackDataReady(IDataResponse resp, ChannelInfo info, ChannelData data) {
        if (resp != null) {
            try {
                resp.onDataReady(this, info, data);
            } catch (Exception e) {
                e.printStackTrace();
                String cmdKey = "";
                String version = "";
                if (data != null) {
                    cmdKey = data.getCommandKey();
                    version = data.getVersion();
                }
                logger.error(Locals.text("protocol.layer.callback", "callbackDataReady", cmdKey,
                        version, e.getMessage()), e);
            }
        }
    }
    
    private void callbackFailed(IDataResponse resp, ChannelInfo info, ChannelData data) {
        if (resp != null) {
            try {
                resp.onFailed(this, info, data);
            } catch (Exception e) {
                e.printStackTrace();
                String cmdKey = "";
                String version = "";
                if (data != null) {
                    cmdKey = data.getCommandKey();
                    version = data.getVersion();
                }
                logger.error(Locals.text("protocol.layer.callback", "callbackFailed", cmdKey,
                        version, e.getMessage()), e);
            }
        }
    }
    
    private void callbackResponse(IDataResponse resp, ChannelInfo info, ChannelData send,
            ChannelData recv) {
        if (resp != null) {
            if (super.sessions.isLoginMode()) {
                ISession session = sessions.getSession(info);
                if (session != null) {
                    session.updateSessionTime();
                }
            }
            try {
                resp.onResponse(this, info, send, recv);
            } catch (Exception e) {
                e.printStackTrace();
                String cmdKey = "";
                String version = "";
                if (recv != null) {
                    cmdKey = recv.getCommandKey();
                    version = recv.getVersion();
                }
                logger.error(Locals.text("protocol.layer.callback", "callbackResponse", cmdKey,
                        version, e.getMessage()), e);
            }
        }
    }
    
    public class RequestResponser implements ITransportResponse {
        
        private final IDataResponse responser;
        
        private RequestResponser(IDataResponse responser) {
            this.responser = responser;
        }
        
        @Override
        public void onField(ChannelInfo info, ChannelData data) {
            DataInfo dataInfo = data.getDataInfo();
            if (ProtocolSender.this.debug && dataInfo.isDebugInfo()) {
                String debugMsg = Strings.replace("RESP-XX < {0} - {1} - {2}:> {3}",
                        dataInfo.getDoc(), data.getCommandKey(),
                        data.getStatus(), data.getMessage());
                ProtocolSender.this.logger.debug(debugMsg);
            }
            ProtocolSender.this.callbackFailed(responser, info, data);
        }
        
        @Override
        public void onResponse(ChannelInfo info, ChannelData send, ChannelData recv) {
            DataInfo dataInfo = send.getDataInfo();
            if (ProtocolSender.this.debug && dataInfo.isDebugInfo()) {
                String debugMsg = Strings.replace("RESP-{0} < {1} - {2} - {3}:> {4}",
                        (recv.isSucceed()) ? "OK" : "XX",
                        dataInfo.getDoc(),
                        send.getCommandKey(), recv.getStatus(),
                        recv.getMessage());
                ProtocolSender.this.logger.debug(debugMsg);
            }
            ProtocolSender.this.callbackResponse(responser, info, send,
                    ProtocolSender.this.deserializeChannelData(info,
                            recv));
        }
        
    }
    
    public class RequestTryResponser implements ITransportResponse {
        
        private final IDataResponse responser;
        
        private final DataInfo dataInfo;
        
        private final List<ChannelInfo> infoList;
        
        private final ChannelData channelData;
        
        private final ISerializer serializer;
        
        private final Serializable dataSerialized;
        
        private final boolean multiMode;
        
        private final long timeout;
        
        private final ResponseMode responseMode;
        
        private RequestTryResponser(IDataResponse responser, DataInfo dataInfo,
                List<ChannelInfo> infoList, ChannelData channelData,
                ISerializer serializer, Serializable dataSerialized,
                boolean multiMode, long timeout, ResponseMode responseMode) {
            this.responser = responser;
            this.dataInfo = dataInfo;
            this.infoList = infoList;
            this.channelData = channelData;
            this.serializer = serializer;
            this.dataSerialized = dataSerialized;
            this.multiMode = multiMode;
            this.timeout = timeout;
            this.responseMode = responseMode;
        }
        
        @Override
        public void onField(ChannelInfo info, ChannelData data) {
            if (ProtocolSender.this.isDebug() && dataInfo.isDebugInfo()) {
                String debugMsg = Strings.replace("RESP-XX < {0} - {1} - {2} :> {3}",
                        dataInfo.getDoc(), data.getCommandKey(),
                        data.getStatus(), data.getMessage());
                ProtocolSender.this.logger.debug(debugMsg);
            }
            if (infoList.size() > 0) {
                ChannelInfo sendChannel = infoList.remove(0);
                Serializable seria = this.dataSerialized;
                if (this.multiMode) {
                    ISerializer serializer = ProtocolSender.this.getSerializer(sendChannel);
                    if (serializer != this.serializer) {
                        seria = ProtocolSender.this.serialize(serializer, dataInfo,
                                channelData.getDataBean());
                    }
                }
                ChannelData sendData = ChannelData.protocolToRemoteRequest(this.channelData,
                        sendChannel, dataInfo,
                        seria);
                ProtocolSender.this.callbackDataReady(this.responser, sendChannel, sendData);
                ProtocolSender.this.transport.send(sendChannel, sendData, this,
                        timeout, responseMode, true);
            } else {
                ProtocolSender.this.callbackFailed(responser, info, data);
            }
        }
        
        @Override
        public void onResponse(ChannelInfo info, ChannelData send, ChannelData recv) {
            if (ProtocolSender.this.debug && this.dataInfo.isDebugInfo()) {
                String debugMsg = Strings.replace("RESP-{0} < {1}-{2}-{3}:> {4}",
                        (recv.isSucceed()) ? "OK" : "XX",
                        dataInfo.getDoc(),
                        send.getCommandKey(), recv.getStatus(),
                        recv.getMessage());
                ProtocolSender.this.logger.debug(debugMsg);
            }
            if (responser != null) {
                ProtocolSender.this.callbackResponse(responser, info, send,
                        ProtocolSender.this.deserializeChannelData(
                                info, recv));
            }
        }
        
    }
    
}
