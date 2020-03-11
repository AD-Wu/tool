package com.x.protocol.layers;

import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IDataResponse;
import com.x.protocol.enums.ResponseMode;

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
    
    private ChannelData deserializeChannelData(ChannelInfo info,ChannelData data){
        return null;
    }
    
}
