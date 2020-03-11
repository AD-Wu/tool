package com.x.protocol.layers;

import com.x.commons.collection.DataSet;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.ResponseResult;

import java.io.Serializable;

/**
 * @Desc TODO
 * @Date 2020-03-10 00:35
 * @Author AD
 */
public class Protocol extends ProtocolActor {
    
    public Protocol(String name) {
        super(name);
    }
    
    @Override
    public ResponseResult response(ChannelInfo info, ChannelData data, Serializable dataSerialized, DataSet dataSet, int status
            , String msg) {
        return null;
    }
    
    @Override
    public ResponseResult responseTry(ChannelInfo info, ChannelData data, Serializable dataSerialized, DataSet dataSet,
            int status, String msg) {
        return null;
    }
    
    @Override
    public void onDataRequest(ChannelInfo info, ChannelData data) throws Exception {
    
    }
    
}
