package com.x.protocol.network.factory.serial;

import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkIO;
import com.x.protocol.network.factory.NetworkService;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;

/**
 * @Desc
 * @Date 2020-03-19 23:06
 * @Author AD
 */
public class SerialConsent extends NetworkConsent implements SerialPortEventListener {
    
    private int serialType = 0;
    
    private SerialPort serialPort;
    
    private SerialInput in;
    
    private SerialOutput out;
    
    public SerialConsent(String name, NetworkService service,
            NetworkConsentType type, SerialPort serialPort, int serialType)
            throws IOException {
        super(name, service, type, false, false);
        this.serialPort = serialPort;
        this.serialType = serialType;
        this.in = new SerialInput(service, this, serialPort.getInputStream());
        this.out = new SerialOutput(service, this, serialPort.getOutputStream());
    }
    
    public NetworkIO getNetworkIO() {
        return new NetworkIO(service, this, in, out);
    }
    
    public SerialPort getSerialPort() {
        return this.serialPort;
    }
    
    @Override
    protected boolean checkDataAvailable() {
        return this.in.available() > 0;
    }
    
    @Override
    protected Object getConsentInfo(String consentType) {
        switch (consentType) {
            case SerialKey.INFO_C_NAME:
                return serialPort.getName();
            case SerialKey.INFO_C_TYPE:
                return this.serialType;
            case SerialKey.INFO_C_BAUD_RATE:
                return serialPort.getBaudRate();
            case SerialKey.INFO_C_DATA_BITS:
                return serialPort.getDataBits();
            case SerialKey.INFO_C_STOP_BITS:
                return serialPort.getStopBits();
            case SerialKey.INFO_C_PARITY:
                return serialPort.getParity();
            case SerialKey.INFO_C_FLOW_CONTROL:
                return serialPort.getFlowControlMode();
            default:
                return service.getInformation(consentType);
            
        }
    }
    
    @Override
    protected void closeConsent() {
        in.close();
        out.close();
        if (serialPort != null) {
            try {
                serialPort.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            serialPort = null;
        }
    }
    
    @Override
    public void serialEvent(SerialPortEvent event) {
        int type = event.getEventType();
        switch (type) {
            case SerialPortEvent.DATA_AVAILABLE:
                super.notifyData(new NetworkIO(service, this, in, out));
            default:
        }
    }
    
}
