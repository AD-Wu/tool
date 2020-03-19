package com.x.protocol.network.factory.serial;

import com.x.commons.collection.DataSet;
import com.x.commons.util.bean.New;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkNotification;
import gnu.io.CommPortIdentifier;

import java.util.Enumeration;
import java.util.List;

/**
 * @Desc TODO
 * @Date 2020-03-19 23:21
 * @Author AD
 */
public class SerialService extends NetworkService {
    // ------------------------ 变量定义 ------------------------
    
    // ------------------------ 构造方法 ------------------------
    
    public SerialService(INetworkNotification notification) {
        super(notification, false);
    }
    
    // ------------------------ 方法定义 ------------------------
    
    @Override
    protected Object getServiceInfo(String serviceProperty) {
        switch (serviceProperty) {
            case SerialKey.INFO_S_PORTS:
                return getPorts(0);
            case SerialKey.INFO_S_PORTS_SERIAL:
                return getPorts(CommPortIdentifier.PORT_SERIAL);
            case SerialKey.INFO_S_PORTS_RS485:
                return getPorts(CommPortIdentifier.PORT_RS485);
            case SerialKey.INFO_S_PORTS_PARALLEL:
                return getPorts(CommPortIdentifier.PORT_PARALLEL);
            case SerialKey.INFO_S_PORTS_RAW:
                return getPorts(CommPortIdentifier.PORT_RAW);
            case SerialKey.INFO_S_PORTS_I2C:
                return getPorts(CommPortIdentifier.PORT_I2C);
            default:
                return super.config.get(serviceProperty);
            
        }
    }
    
    @Override
    protected void serviceStart() {
        super.notifyServiceStart();
    }
    
    @Override
    protected void serviceStop() {
    
    }
    
    @Override
    public String getServiceInfo() {
        return "SERIAL";
    }
    
    @Override
    public INetworkConsent connect(String name, DataSet data) throws Exception {
        return null;
    }
    
    // ------------------------ 私有方法 ------------------------
    private List<CommPortIdentifier> getPorts(int portType) {
        Enumeration identifiers = CommPortIdentifier.getPortIdentifiers();
        List<CommPortIdentifier> result = New.list();
        while (true) {
            CommPortIdentifier identifier;
            do {
                if (!identifiers.hasMoreElements()) {
                    return result;
                }
                identifier = (CommPortIdentifier) identifiers.nextElement();
            } while (portType != 0 && identifier.getPortType() != portType);
            result.add(identifier);
        }
    }
    
}
