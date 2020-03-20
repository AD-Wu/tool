package com.x.protocol.layers.transport.channel.core;

import com.x.commons.local.Locals;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IProtocolReader;
import com.x.protocol.core.IProtocolSender;
import com.x.protocol.layers.Protocol;
import com.x.protocol.layers.transport.Transport;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkIO;
import com.x.protocol.network.interfaces.INetworkOutput;

import static com.x.protocol.network.factory.custom.CustomKey.*;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 13:44
 */
public abstract class Channel extends ChannelClients {
    
    public Channel(Transport transport, Protocol protocol) {
        super(transport, protocol);
    }
    
    @Override
    public boolean onConsentData(INetworkIO io) throws Exception {
        if (!super.isStopped() && io != null) {
            NetworkConsent consent = (NetworkConsent)io.getConsent();
            if (consent.isClosed()) {
                return false;
            } else {
                IProtocolReader reader = super.getReader(io);
                if (reader == null) {
                    return false;
                } else {
                    ChannelInfo info = this.getChannelInfo(consent, io);
                    ChannelData data = null;
                    
                    boolean error;
                    try {
                        synchronized (reader) {
                            try {
                                reader.init(this.protocol, info, io);
                                if (reader.nextPackage()) {
                                    data = reader.outputData();
                                    reader.reset();
                                }
                                return true;
                            } catch (Exception e) {
                                reader.reset();
                                data = null;
                                logger.error(Locals.text("protocol.layer.reader.data", info, e.getMessage()), e);
                                error = true;
                            }
                        }
                    } finally {
                        if (data != null) {
                            if (protocol.getSessionManager().isLoginCommand(data.getCommand(), data.getControl())) {
                                try {
                                    this.notifyDataReady(info, data);
                                } catch (Exception e) {
                                    logger.error(Locals.text("protocol.layer.reader.data", info, e.getMessage()), e);
                                }
                                consent.resetReading();
                            } else {
                                consent.resetReading();
                                
                                try {
                                    this.notifyDataReady(info, data);
                                } catch (Exception e) {
                                    logger.error(Locals.text("protocol.layer.reader.data", info,e.getMessage()), e);
                                }
                            }
                        } else {
                            consent.resetReading();
                        }
                    }
                    return error;
                }
            }
        } else {
            return false;
        }
        
    }
    
    public boolean send(ChannelInfo info, ChannelData data) {
        if (!this.isStopped() && data != null) {
            INetworkIO io = info.getNetworkIO();
            if (io == null) {
                io = info.getConsent().getNetworkIO();
                if (io == null) {
                    return false;
                }
            }
            
            IProtocolSender sender = this.getSender();
            if (sender == null) {
                return false;
            } else {
                boolean unsend = false;
                
                boolean sendResult;
out:
                {
                    boolean isSucceed;
                    try {
                        unsend = true;
                        sendResult = sender.send(this.protocol, info, io, data);
                        unsend = false;
                        break out;
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(Locals.text("protocol.layer.sender.data", info,e.getMessage()), e);
                        isSucceed = false;
                        unsend = false;
                    } finally {
                        if (unsend) {
                            closeOutput(io);
                        }
                    }
                    closeOutput(io);
                    return isSucceed;
                }
                closeOutput(io);
                return sendResult;
            }
        } else {
            return false;
        }
    }
    protected void setChannelInfo(INetworkConsent consent,ChannelInfo info){
        info.setLocalHost(Strings.of(consent.getInformation(INFO_C_LOCAL_HOST)));
        info.setLocalPort(Converts.toInt(consent.getInformation(INFO_C_LOCAL_PORT)));
        info.setRemoteHost(Strings.of(consent.getInformation(INFO_C_REMOTE_HOST)));
        info.setRemotePort(Converts.toInt(consent.getInformation(INFO_C_REMOTE_PORT)));
    }
    private void closeOutput(INetworkIO io){
        INetworkOutput out = io.getOutput();
        if (out != null) {
            try {
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
