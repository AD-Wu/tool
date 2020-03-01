package com.x.protocol.network.factory.socket;

import com.x.commons.local.Locals;
import com.x.commons.util.collection.XArrays;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkOutput;
import com.x.protocol.network.factory.NetworkService;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Desc TODO
 * @Date 2020-03-02 00:32
 * @Author AD
 */
public final class SocketOutput extends NetworkOutput implements ISocketOutput {
    
    private OutputStream out;
    
    private Object outLock = new Object();
    
    public SocketOutput(NetworkService service, NetworkConsent concent, OutputStream out) {
        super(service, concent);
        this.out = out;
    }
    
    @Override
    public boolean send(byte[] bytes) {
        if (!XArrays.isEmpty(bytes)) {
            try {
                synchronized (outLock) {
                    if (out == null) {
                        return false;
                    }
                    out.write(bytes);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                super.service.notifyError(super.concent, Locals.text("protocol.socket.send.byte", e.getMessage()));
                super.concent.close();
                return false;
            }
            
        }
        return false;
    }
    
    @Override
    public void flush() {
        try {
            synchronized (outLock) {
                if (out == null) {
                    return ;
                }
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            super.service.notifyError(super.concent, Locals.text("protocol.socket.flush", e.getMessage()));
            super.concent.close();
        }
    }
    
    void close(){
    
        try {
            synchronized (outLock){
                if(out!=null){
                    out.close();
                    out = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
