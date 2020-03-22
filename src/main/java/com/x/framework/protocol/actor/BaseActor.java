package com.x.framework.protocol.actor;

import com.x.framework.protocol.BaseProtocol;
import com.x.protocol.core.*;
import com.x.protocol.enums.ProtocolStatus;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @Desc
 * @Date 2020-03-22 22:02
 * @Author AD
 */
public abstract class BaseActor extends BaseProtocol implements IDataActor {
    
    protected ChannelInfo channelInfo;
    
    protected ChannelData channelData;
    
    protected boolean needResponse;
    
    private IProtocol prtc;
    
    private boolean hasResponse = false;
    
    protected BaseActor(String name, boolean needResponse) {
        super(name);
        this.needResponse = needResponse;
    }
    
    protected abstract boolean onReceive(IProtocol prtc, ChannelInfo info, ChannelData data) throws Exception;
    
    protected abstract void onResponse(IProtocol prtc, ResponseResult result);
    
    @Override
    public void onDataRequest(IProtocol prtc, ChannelInfo info, ChannelData data) throws Exception {
        this.prtc = prtc;
        this.channelInfo = info;
        this.channelData = data;
        if (!onReceive(prtc, info, data)) {
            responseError(ProtocolStatus.LICENSE_FORBIDDEN, info.text("framework.actor.license"));
        } else {
            DataInfo dataInfo = data.getDataInfo();
            Method method = dataInfo.getMethod();
            if (method == null) {
                throw new Exception(info.text("framework.actor.method.err", null, data.getCommandKey(), data.getVersion()));
            } else {
                Class<?> dataClass = dataInfo.getDataClass();
                Object result;
                if (dataClass == null) {
                    result = method.invoke(this);
                } else {
                    result = method.invoke(this, data.getDataBean());
                }
                
                Class<?> returnType = method.getReturnType();
                if ("void".equals(returnType.getName())) {
                    if (needResponse) {
                        responseSucceed(null);
                    }
                } else if (result == null) {
                    responseError(ProtocolStatus.OTHERS, info.text("framework.actor.err"));
                } else {
                    responseSucceed((Serializable) result);
                }
            }
        }
    }
    
    protected ResponseResult responseSucceed(Serializable dataBean) {
        if (!hasResponse && prtc != null) {
            this.hasResponse = true;
            ResponseResult result = prtc.response(channelInfo, channelData, dataBean, channelData.getDataSet(),
                    ProtocolStatus.OK, null);
            this.onResponse(prtc, result);
            return result;
        } else {
            return null;
        }
    }
    
    protected ResponseResult responseSucceedTry(ChannelInfo[] infos, Serializable dataBean) {
        if (!hasResponse && prtc != null) {
            this.hasResponse = true;
            ResponseResult result = prtc.responseTry(infos, channelData, dataBean,
                    channelData.getDataSet(), ProtocolStatus.OK, null);
            this.onResponse(prtc, result);
            return result;
        } else {
            return null;
        }
    }
    
    protected ResponseResult responseError(int status, String message) {
        return responseError(status, message, null);
    }
    
    protected ResponseResult responseError(int status, String message, Serializable dataBean) {
        if (!hasResponse && prtc != null) {
            this.hasResponse = true;
            ResponseResult result = prtc.response(channelInfo, channelData, dataBean, channelData.getDataSet(), status, message);
            this.onResponse(prtc, result);
            return result;
        } else {
            return null;
        }
    }
    
    protected ResponseResult responseErrorTry(ChannelInfo[] infos, int status, String message) {
        return responseErrorTry(infos, status, message, null);
    }
    
    protected ResponseResult responseErrorTry(ChannelInfo[] infos, int status, String message, Serializable dataBean) {
        if (!hasResponse && prtc != null) {
            this.hasResponse = true;
            ResponseResult result = prtc.responseTry(infos, channelData, dataBean,
                    channelData.getDataSet(), status, message);
            this.onResponse(prtc, result);
            return result;
        } else {
            return null;
        }
    }
    
}
