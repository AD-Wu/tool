package com.x.protocol.layers.transport;

import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IChannel;
import com.x.protocol.enums.ProtocolStatus;
import com.x.protocol.enums.ResponseMode;
import com.x.protocol.layers.Protocol;
import com.x.protocol.layers.transport.interfaces.ITransportResponse;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Date 2020-03-11 00:07
 * @Author AD
 */
public class TransportResponser extends TransportService {

    private final Object callbackLock = new Object();

    private Map<String, CallbackInfo> callbacks = New.concurrentMap();

    private boolean isChanged = true;

    private CallbackInfo[] callbackArray;


    public TransportResponser(Protocol protocol) {
        super(protocol);
    }


    protected void checkCallbackTimeout(long now) {
        if (isChanged) {
            synchronized (callbackLock) {
                this.isChanged = false;
                callbackArray = callbacks.values().toArray(new CallbackInfo[0]);
            }
        }
        if (!XArrays.isEmpty(callbackArray)) {
            int count = 0;
            for (CallbackInfo callback : callbackArray) {
                ++count;
                if (callback.checkTimeout(now)) {
                    synchronized (callbackLock) {
                        callback = callbacks.remove(callback.getKey());
                        if (callback != null && !isChanged) {
                            this.isChanged = true;
                        }
                    }

                    if (callback != null) {
                        IChannel chn = callback.getChannelInfo().getChannel();
                        CallbackInfo call = callback;
                        if (!chn.runSchedule(() -> {
                            TransportResponser.this.callbackErrorResponse(call.getResponse(),
                                                                          call.getChannelInfo(),
                                                                          call.getData(),
                                                                          ProtocolStatus.REQUEST_FAILURE);
                        })) {
                            this.callbackErrorResponse(call.getResponse(),
                                                       call.getChannelInfo(),
                                                       call.getData(),
                                                       ProtocolStatus.REQUEST_FAILURE);
                        }
                    }
                }
                if (count % 20 == 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }
    }

    public void changeTimeout(ChannelInfo info, ChannelData data, int change) {
        String callbackKey = getCallbackKey(data, info);
        CallbackInfo callback = callbacks.remove(callbackKey);
        if (callback != null) {
            callback.changeTimeout(change);
        }
    }

    public void setTimeout(ChannelInfo info, ChannelData data, long timeout) {
        String callbackKey = getCallbackKey(data, info);
        CallbackInfo callback = callbacks.remove(callbackKey);
        if (callback != null) {
            callback.resetTimeout(timeout);
        }
    }

    public CallbackInfo removeTimeout(ChannelInfo info, ChannelData data) {
        String callbackKey = getCallbackKey(data, info);
        synchronized (callbackLock) {
            CallbackInfo callback = callbacks.remove(callbackKey);
            if (callback != null && !this.isChanged) {
                this.isChanged = true;
            }
            return callback;
        }
    }

    public boolean containsCallbackInfo(ChannelInfo info, ChannelData data) {
        if (data.isRequest()) {
            return false;
        } else if (callbacks.containsKey(getCallbackKey(data, info))) {
            return true;
        } else {
            return data.isNoCtrlCallbackKey() &&
                    callbacks.containsKey(getNoCtrlCallbackKey(data, info));
        }
    }

    public CallbackInfo getCallbackInfo(ChannelInfo info, ChannelData data) {
        CallbackInfo callback = callbacks.get(getCallbackKey(data, info));
        if (callback == null) {
            callback = callbacks.get(getNoCtrlCallbackKey(data, info));
            if (callback == null) {
                return null;
            } else {
                if (!callback.getData().isNoCtrlCallbackKey()) {
                    return null;
                }
            }
        }
        return callback;
    }

    public boolean callbackResponse(ChannelInfo info, ChannelData data) {
        CallbackInfo callback = getCallbackInfo(info, data);
        if (callback == null) {
            return false;
        } else {
            ResponseMode mode = callback.getResponseMode();
            if (data.isRequest()) {
                if (mode == ResponseMode.RESP_ONLY) {
                    return false;
                }
            } else {
                if (mode == ResponseMode.REQ_ONLY_SKIP_RESP) {
                    return false;
                }
                if (data.isSucceed() && mode == ResponseMode.REQ_ONLY) {
                    return false;
                }
            }

            synchronized (callbackLock) {
                callback = callbacks.remove(callback.getKey());
                if (callback == null) {
                    return false;
                }
                if (!this.isChanged) {
                    this.isChanged = true;
                }
            }
            ChannelData recv = callback.getData();
            if (recv.getControl().length() > 0 && data.getControl().length() == 0) {
                data.setControl(recv.getControl());
            }
            ITransportResponse response = callback.getResponse();

            try {
                response.onResponse(info, recv, data);
            } catch (Exception e) {
                logger.error(Locals.text("protocol.layer.responser.err"), e);
            }
            return true;
        }
    }


    public void callbackErrorResponse(ITransportResponse resp, ChannelInfo info, ChannelData data
            , int status) {
        if (resp != null) {
            data.setStatus(status);
            try {
                resp.onField(info, data);
            } catch (Exception e) {
                logger.error(Locals.text("protocol.layer.responser.err"), e);
            }
        }
    }

    protected void addTimeout(ChannelInfo info, ChannelData data, ITransportResponse resp,
                              long timeout, ResponseMode mode) {
        if (resp != null) {
            if (timeout <= 0) {
                timeout = info.getChannel().getSendTimeout();
            }
            String callbackKey = getCallbackKey(data, info);
            synchronized (callbackLock) {
                if (!callbacks.containsKey(callbackKey)) {
                    callbacks.put(callbackKey,
                                  new CallbackInfo(callbackKey, info, data, resp, timeout
                                          , mode));
                    if (!this.isChanged) {
                        this.isChanged = true;
                    }
                }
            }
        }
    }

    private String getCallbackKey(ChannelData data, ChannelInfo info) {
        return data.getCallbackKey() + "|" + info.getChannelInfoKey();
    }

    private String getNoCtrlCallbackKey(ChannelData data, ChannelInfo info) {
        return data.getNoCtrlCallbackKey() + "|" + info.getChannelInfoKey();
    }
}
