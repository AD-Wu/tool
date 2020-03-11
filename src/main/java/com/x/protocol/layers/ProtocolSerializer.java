package com.x.protocol.layers;

import com.x.commons.local.Locals;
import com.x.protocol.core.*;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-11 23:17
 * @Author AD
 */
public abstract class ProtocolSerializer extends ProtocolService {
    
    private final Object lock = new Object();
    
    public ProtocolSerializer(String name) {
        super(name);
    }
    
    protected ISerializer getSerializer(ChannelInfo info) {
        ISerializer seria;
        if (sessions.isMultiSerializer()) {
            ISession session = sessions.getSession(info);
            if (session != null) {
                seria = session.getSerializer();
                if (seria != null) {
                    return seria;
                }
            }
        }
        IChannel channel = info.getChannel();
        seria = channel.getSerializer();
        if (seria != null) {
            return seria;
        } else {
            synchronized (lock) {
                seria = channel.getSerializer();
                if (seria != null) {
                    return seria;
                } else {
                    seria = presentation.createSerializer(channel.getName());
                    channel.setSerializer(seria);
                    return seria;
                }
            }
        }
    }
    
    public Serializable serialize(ISerializer seria, DataInfo info, Serializable data) {
        if (data == null) {
            return data;
        } else {
            try {
                return seria.serialize(info, data);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.serializer", seria, "serialize", data, e.getMessage()));
                return null;
            }
        }
    }
    
    public Serializable deserialize(ISerializer seria, DataInfo info, Serializable serialized) {
        if (serialized == null) {
            return serialized;
        } else {
            try {
                return seria.deserialize(info, serialized);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.serializer", seria, "deserialize", serialized, e.getMessage()));
                return null;
            }
        }
    }
    
}
