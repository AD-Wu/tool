package com.x.protocol.network.factory.serial;

import com.x.commons.local.Locals;
import com.x.commons.util.collection.XArrays;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkInput;
import com.x.protocol.network.factory.NetworkService;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Desc 串口输入流
 * @Date 2020-03-19 22:46
 * @Author AD
 */
public class SerialInput extends NetworkInput implements ISerialInput {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 输入流对象
     */
    private InputStream in;
    
    /**
     * 输入流锁
     */
    private final Object lock = new Object();
    
    // ------------------------ 构造方法 ------------------------
    
    public SerialInput(NetworkService service, NetworkConsent consent, InputStream in) {
        super(service, consent);
        this.in = in;
    }
    
    // ------------------------ 方法定义 ------------------------
    @Override
    public int available() {
        try {
            synchronized (lock) {
                return in == null ? 0 : in.available();
            }
        } catch (IOException e) {
            e.printStackTrace();
            super.service.notifyError(super.consent, Locals.text("protocol.serial.available", e.getMessage()));
            super.consent.close();
            return 0;
        }
    }
    
    @Override
    public int read() {
        try {
            synchronized (lock) {
                return in == null ? -1 : in.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
            super.service.notifyError(super.consent, Locals.text("protocol.serial.read.byte", e.getMessage()));
            super.consent.close();
            return -1;
        }
    }
    
    @Override
    public int read(byte[] bytes) {
        if (XArrays.isEmpty(bytes)) return -1;
        try {
            synchronized (lock) {
                return in == null ? -1 : in.read(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            super.service.notifyError(super.consent, Locals.text("protocol.serial.read.bytes", e.getMessage()));
            super.consent.close();
            return -1;
        }
    }
    
    @Override
    public void reset() {
        try {
            synchronized (lock) {
                if (in == null) {
                    return;
                }
                in.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
            super.service.notifyError(super.consent, Locals.text("protocol.serial.reset", e.getMessage()));
            super.consent.close();
        }
    }
    
    void close() {
        try {
            synchronized (lock) {
                if (in != null) {
                    in.close();
                    in = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
