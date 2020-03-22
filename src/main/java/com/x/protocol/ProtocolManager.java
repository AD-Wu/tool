package com.x.protocol;

import com.x.commons.collection.DataSet;
import com.x.commons.collection.NameValue;
import com.x.protocol.config.ServiceConfig;
import com.x.protocol.core.IProtocol;
import com.x.protocol.core.IProtocolInitializer;
import com.x.protocol.core.IStatusNotification;
import com.x.protocol.layers.Protocol;
import com.x.protocol.layers.application.config.InitializerConfig;

import java.util.List;

/**
 * @Desc
 * @Date 2020-03-22 00:39
 * @Author AD
 */
public class ProtocolManager {
    
    public static IProtocol start(ServiceConfig config, IStatusNotification status) throws Exception {
        InitializerConfig ic = config.getApplication().getInitializer();
        if (ic != null) {
            String clazzName = ic.getClazz();
            IProtocolInitializer initializer = null;
            DataSet params = null;
            if (clazzName != null && clazzName.length() > 0) {
                Class clazz = Class.forName(clazzName);
                initializer = (IProtocolInitializer) clazz.newInstance();
                List<NameValue> nvList = ic.getParameters();
                if (nvList != null && nvList.size() > 0) {
                    params = new DataSet(nvList);
                }
            }
            
            return start(config, initializer, status, params);
        } else {
            return start(config, (IProtocolInitializer) null, status, (DataSet) null);
        }
    }
    
    public static IProtocol start(ServiceConfig config, IProtocolInitializer initializer, IStatusNotification status,DataSet params) throws Exception {
        if (config == null) {
            return null;
        } else {
            Protocol protocol = new Protocol(config.getName());
            return protocol.start(config, initializer, params, status) ? protocol : null;
        }
    }
    
}
