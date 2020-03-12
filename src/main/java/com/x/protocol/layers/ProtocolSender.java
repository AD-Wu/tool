package com.x.protocol.layers;

import com.x.commons.local.Locals;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.*;
import com.x.protocol.enums.ProtocolStatus;
import com.x.protocol.enums.ResponseMode;
import com.x.protocol.layers.transport.interfaces.ITransportResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc TODO
 * @Date 2020-03-12 00:38
 * @Author AD
 */
public abstract class ProtocolSender extends ProtocolSerializer {

    public ProtocolSender(String name) {
        super(name);
    }

    @Override
    public int request(ChannelInfo[] infos, ChannelData data, IDataResponse resp, long index, ResponseMode mode) {
        return 0;
    }

    @Override
    public boolean requestTry(ChannelInfo[] infos, ChannelData data, IDataResponse resp, long index, ResponseMode mode) {
        return false;
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
                resp.onDataResponse(this, info, data);
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
