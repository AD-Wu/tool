package com.x.protocol.network.factory.serial;

import com.x.commons.collection.DataSet;
import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkNotification;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

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
        if (data != null && !super.isStopped()) {
            String serialName = data.getString("name");
            if (serialName != null && serialName.length() != 0) {
                SerialPort serialPort = null;

                try {
                    CommPortIdentifier identifier = null;
                    int type = data.getInt("type");
                    if (type <= 0) {
                        identifier = CommPortIdentifier.getPortIdentifier(serialName);
                    } else {
                        Enumeration identifiers = CommPortIdentifier.getPortIdentifiers();

                        while (identifiers.hasMoreElements()) {
                            CommPortIdentifier next = (CommPortIdentifier) identifiers.nextElement();
                            if (next.getPortType() == type && serialName.equals(next.getName())) {
                                identifier = next;
                                break;
                            }
                        }
                    }

                    if (identifier != null) {
                        int timeout = data.getInt("connectionTimeout");
                        if (timeout <= 0) {
                            timeout = 2000;
                        }

                        serialPort = (SerialPort) identifier.open(this.getName(), timeout);
                        int rate = data.getInt("baudRate");
                        if (rate <= 0) {
                            rate = 9600;
                        }

                        int dataBits = data.getInt("dataBits");
                        if (dataBits <= 4) {
                            dataBits = 8;
                        }

                        int stopBits = data.getInt("stopBits");
                        if (stopBits <= 0) {
                            stopBits = 1;
                        }

                        int parity = data.getInt("parity");
                        if (parity <= 0) {
                            parity = 0;
                        }

                        int flowControl = data.getInt("flowControl");
                        if (flowControl <= 0) {
                            flowControl = 0;
                        }

                        int readTimeout = data.getInt("readTimeout", -1);
                        if (readTimeout < 0) {
                            readTimeout = 500;
                        }

                        serialPort.setSerialPortParams(rate, dataBits, stopBits, parity);
                        serialPort.setFlowControlMode(flowControl);
                        if (readTimeout > 0) {
                            serialPort.enableReceiveTimeout(readTimeout);
                        }

                        serialPort.setRTS(data.getBoolean("rts", true));
                        SerialConsent consent = new SerialConsent(name, this,
                                                                  NetworkConsentType.LOCAL_TO_REMOTE,
                                                                  serialPort, type);
                        if (consent.start()) {
                            return consent;
                        }
                    }

                    return null;
                } catch (Exception e) {
                    if (serialPort != null) {
                        try {
                            serialPort.close();
                        } catch (Exception ex) {
                        }
                    }

                    throw new Exception(Locals.text("protocol.serial.start.err",
                                                    name, serialName, e.toString()));
                }
            } else {
                throw new Exception(Locals.text("protocol.serial.start", name, serialName));
            }
        } else {
            return null;
        }

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
