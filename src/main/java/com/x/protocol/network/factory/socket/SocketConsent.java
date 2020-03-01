package com.x.protocol.network.factory.socket;

import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkIO;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.interfaces.INetworkIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Desc TODO
 * @Date 2020-03-02 00:51
 * @Author AD
 */
public class SocketConsent extends NetworkConsent {
    
    private Socket socket;
    
    private SocketInput in;
    
    private SocketOutput out;
    
    /**
     * Socket应答对象构造方法
     *
     * @param name    网络应答对象名称
     * @param service 网络服务对象
     * @param type    网络应答对象类型
     * @param socket  socket客户端
     */
    public SocketConsent(String name, NetworkService service, NetworkConsentType type, Socket socket) throws Exception {
        super(name, service, type, false, true);
        this.socket = socket;
        this.in = new SocketInput(service, this, socket.getInputStream());
        this.out = new SocketOutput(service, this, socket.getOutputStream());
        // 获取本地socket host和port
        InetSocketAddress local = socket == null ? null : (InetSocketAddress) socket.getLocalSocketAddress();
        InetAddress addr = local == null ? null : local.getAddress();
        this.localHost = addr == null ? null : addr.getHostAddress();
        this.localPort = local == null ? 0 : local.getPort();
        
        // 获取远端socket host和port
        InetSocketAddress remote = socket == null ? null : (InetSocketAddress) socket.getRemoteSocketAddress();
        addr = remote == null ? null : remote.getAddress();
        this.remoteHost = addr == null ? null : addr.getHostAddress();
        this.remotePort = remote == null ? 0 : remote.getPort();
    }
    
    public INetworkIO getNetworkIO() {
        return new NetworkIO(super.service, this, in, out);
    }
    
    @Override
    protected boolean checkDataAvailable() {
        return this.in.available() > 0;
    }
    
    @Override
    protected void closeConsent() {
        in.close();
        out.close();
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket = null;
        }
        
    }
    
    @Override
    protected Object getConsentInfo(String consentType) {
        switch (consentType) {
            case "concentLocalHost":
                return localHost;
            case "concentLocalPort":
                return localPort;
            case "concentRemoteHost":
                return remoteHost;
            case "concentRemotePort":
                return remotePort;
            default:
                return super.service.getInformation(consentType);
        }
    }
    
}
