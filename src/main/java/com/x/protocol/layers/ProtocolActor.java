package com.x.protocol.layers;

import com.x.commons.collection.DataSet;
import com.x.commons.local.Locals;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.*;
import com.x.protocol.enums.ProtocolStatus;
import com.x.protocol.enums.ResponseMode;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-12 00:47
 * @Author AD
 */
public abstract class ProtocolActor extends ProtocolSender {
    
    public ProtocolActor(String name) {
        super(name);
    }
    
    @Override
    public ResponseResult response(
            ChannelInfo info, ChannelData data, Serializable dataSerialized,
            DataSet dataSet, int status, String msg) {
        if (info != null && data != null) {
            DataInfo dataInfo = super.application.getFromRemoteResponseConfig(data);
            if (dataInfo == null) {
                super.logger.error(Locals.text("protocol.layer.response", data.getCommandKey(),
                        data.getVersion(), dataSerialized));
                return new ResponseResult(info, null, false);
            } else {
                Serializable dataBen = null;
                if (dataSerialized != null) {
                    dataBen = this.serialize(super.getSerializer(info), dataInfo, dataSerialized);
                }
                
                ChannelData respData = ChannelData.protocolToRemoteResponse(data, dataInfo, dataSerialized, dataBen, dataSet,
                        status, msg);
                if (this.debug && dataInfo.isDebugInfo()) {
                    String debugMsg = Strings.replace("RES-{0}{1} - {2} - {3}:> {4}",
                            respData.isSucceed() ? "OK > " : "XX > ", dataInfo.getDoc(),
                            respData.getCommandKey(), respData.getStatus(), respData.getMessage());
                    this.logger.debug(debugMsg);
                }
                
                return new ResponseResult(info, respData, this.transport.send(info, respData, null, 0,
                        ResponseMode.RESP_ONLY, false));
            }
        } else {
            return new ResponseResult(info, null, false);
        }
    }
    
    @Override
    public ResponseResult responseTry(
            ChannelInfo[] infos, ChannelData data, Serializable dataSerialized,
            DataSet dataSet, int status, String msg) {
        if (data != null && infos != null && infos.length != 0) {
            DataInfo dataInfo = super.application.getFromRemoteResponseConfig(data);
            if (dataInfo == null) {
                super.logger.error(Locals.text("protocol.layer.response", data.getCommandKey(), data.getVersion(),
                        dataSerialized));
                return new ResponseResult(null, null, false);
            } else {
                boolean multi = false;
                ISerializer serializer = null;
                Serializable dataBean = null;
                int var13;
                if (dataSerialized != null) {
                    multi = this.sessions.isMultiSerializer();
                    if (!multi && infos.length > 1) {
                        IChannel channel = infos[0].getChannel();
                        
                        for (int i = 1, L = infos.length; i < L; ++i) {
                            if (infos[i].getChannel() != channel) {
                                multi = true;
                                break;
                            }
                        }
                    }
                    
                    serializer = this.getSerializer(infos[0]);
                    dataBean = this.serialize(serializer, dataInfo, dataSerialized);
                }
                
                ChannelInfo channelInfo = null;
                ChannelData channelData = null;
                var13 = 0;
                
                for (int var14 = infos.length; var13 < var14; ++var13) {
                    channelInfo = infos[var13];
                    Serializable var15 = dataBean;
                    if (multi && var13 > 0) {
                        ISerializer var16 = this.getSerializer(channelInfo);
                        if (var16 != serializer) {
                            var15 = this.serialize(var16, dataInfo, dataSerialized);
                        }
                    }
                    
                    channelData = ChannelData.protocolToRemoteResponse(data, dataInfo, dataSerialized, var15, dataSet, status,
                            msg);
                    if (this.debug && dataInfo.isDebugInfo()) {
                        String debugMsg = Strings.replace("RES-{0}{1} - {2} - {3}:> {4}",
                                channelData.isSucceed() ? "OK > " : "XX > ",
                                dataInfo.getDoc(), channelData.getCommandKey(),
                                channelData.getStatus(), channelData.getMessage());
                        this.logger.debug(debugMsg);
                    }
                    
                    if (this.transport.send(channelInfo, channelData, null, 0, ResponseMode.RESP_ONLY, false)) {
                        return new ResponseResult(channelInfo, channelData, true);
                    }
                }
                
                return new ResponseResult(channelInfo, channelData, false);
            }
        } else {
            return new ResponseResult(null, null, false);
        }
    }
    
    @Override
    public void onDataRequest(ChannelInfo info, ChannelData data) throws Exception {
        DataInfo dataInfo = super.application.getFromRemoteRequestConfig(data);
        boolean login = false;
        if (super.sessions.isLoginMode()) {
            ISession session = this.sessions.getSession(info);
            if (session == null) {
                if (!super.sessions.isLoginRequest(data, dataInfo)) {
                    super.logger.warn(Locals.text("protocol.layer.login", info, data.getCommandKey(), data.getVersion()));
                    this.response(info, data, null, data.getDataSet(), 5, info.text("protocol.layer.login.err"));
                    info.getConsent().close();
                    return;
                }
                
                login = true;
            } else {
                session.updateSessionTime();
            }
        } else {
            info.getConsent().setLoginMark(true);
        }
        
        if (dataInfo == null) {
            super.logger.error(Locals.text("protocol.layer.config", info, data.getCommandKey(), data.getVersion()));
            this.response(info, data, null, data.getDataSet(), 4, info.text("protocol.layer.config.err"));
        } else {
            data.setDataInfo(dataInfo);
            Serializable dataSerialized = data.getDataSerialized();
            if (dataSerialized != null && !"".equals(dataSerialized)) {
                ISerializer serializer = super.getSerializer(info);
                Serializable dataBean = super.deserialize(serializer, dataInfo, dataSerialized);
                if (dataBean == null && dataInfo.getDataClass() != null) {
                    this.response(info, data, null, data.getDataSet(), 4, info.text("protocol.layer.data.err"));
                    return;
                }
                
                data.setDataBean(dataBean);
            }
            
            if (this.debug && dataInfo.isDebugInfo()) {
                super.logger.debug(
                        "REQ-FR < " + dataInfo.getDoc() + " - " + data.getCommandKey() + ":> " + data.getDataSerialized());
            }
            
            Class<?>[] actors = dataInfo.getActors();
            
            for (int i = 0, L = actors.length; i < L; ++i) {
                Class<?> actorClass = actors[i];
                
                try {
                    IDataActor dataActor = (IDataActor) actorClass.newInstance();
                    dataActor.onDataRequest(this, info, data);
                } catch (Exception e) {
                    e.printStackTrace();
                    Throwable cause = e.getCause();
                    String msg = cause == null ? e.getMessage() : cause.getMessage();
                    super.logger.error(Locals.text("protocol.layer.actor.err", info, msg, data.getCommandKey(),
                            data.getVersion()));
                    this.response(info, data, null, data.getDataSet(), ProtocolStatus.EXPECTATION, msg);
                    break;
                }
            }
            
            if (login && this.sessions.isChannelLogin(info) || dataInfo.isSkipLogin()) {
                this.sessions.resetSecurity(info);
            }
            
        }
    }
    
}
