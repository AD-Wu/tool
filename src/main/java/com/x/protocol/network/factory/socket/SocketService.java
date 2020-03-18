package com.x.protocol.network.factory.socket;

import com.x.commons.collection.DataSet;
import com.x.commons.local.Locals;
import com.x.commons.util.string.Strings;
import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkNotification;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @Desc TODO
 * @Date 2020-03-02 20:09
 * @Author AD
 */
public class SocketService extends NetworkService {
    
    private ServerSocket serverSocket;
    
    private Object socketLock = new Object();
    
    private String localHost;
    
    private int localPort = 0;
    
    private String svcInfoText;
    
    public SocketService(INetworkNotification notification) {
        super(notification, false);
    }
    
    @Override
    public INetworkConsent connect(String name, DataSet data) throws Exception {
        if (name != null && !super.isStopped()) {
            String host = data.getString("host");
            if (!Strings.isNull(host)) {
                int port = data.getInt("port");
                if (port <= 0) {
                    throw new Exception(Locals.text("protocol.socket.client.port", host,port));
                } else {
                    String localHost = data.getString("bindHost");
                    int localPort = data.getInt("bindPort");
                    Socket socket = null;
                
                    try {
                        if (!Strings.isNull(localHost) && localPort > 0) {
                            socket = new Socket(host, port, InetAddress.getByName(localHost), localPort);
                        } else {
                            socket = new Socket(host, port);
                        }
                        socket.setKeepAlive(true);
                        socket.setSoTimeout(1);
                        socket.setTrafficClass(20);
                        SocketConsent consent = new SocketConsent(name, this, NetworkConsentType.LOCAL_TO_REMOTE, socket);
                        return consent.start() ? consent : null;
                    } catch (Exception e) {
                        if (socket != null) {
                            try {
                                socket.close();
                            } catch (Exception ex) {
                            }
                        }
                        throw new Exception(Locals.text("protocol.socket.client.start", name,e.getMessage()));
                    }
                }
            } else {
                throw new Exception(Locals.text("protocol.socket.client.host",name,host));
            }
        } else {
            return null;
        }
    }
    
    @Override
    protected void serviceStart() {
        if (!super.isServerMode()) {
            super.notifyServiceStart();
        } else {
            int port = super.config.getInt("port", 0);
            if (port <= 0) {
                super.notifyError(null, Locals.text("protocol.socket.start", port));
                super.stop();
            } else {
                String host = super.config.getString("host");
                try {
                    if (!Strings.isNull(host) && !"127.0.0.1".equals(host) && !"localhost".equals(host)) {
                        serverSocket = new ServerSocket(port, 100, InetAddress.getByName(host));
                    } else {
                        serverSocket = new ServerSocket(port, 100);
                    }
                    serverSocket.setSoTimeout(0);
                } catch (Exception e) {
                    e.printStackTrace();
                    super.notifyError(null, Locals.text("protocol.socket.start.err", host, port, e.getMessage()));
                    super.stop();
                    return;
                }
                super.notifyServiceStart();
                
                while (!super.isStopped() && serverSocket != null) {
                    SocketConsent consent = null;
                    try {
                        Socket socket = serverSocket.accept();
                        if (super.isStopped()) {
                            if (socket != null) {
                                socket.close();
                            }
                            break;
                        }
                        consent = new SocketConsent("", this, NetworkConsentType.REMOTE_TO_LOCAL, socket);
                        socket.setKeepAlive(true);
                        socket.setSoTimeout(1);
                        socket.setTrafficClass(20);
                        consent.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (consent != null) {
                            try {
                                consent.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        super.notifyError(consent, Locals.text("protocol.socket.conn.err", e.getMessage()));
                        
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e1) {
                        }
                    }
                }
                super.stop();
            }
        }
    }
    
    @Override
    protected void serviceStop() {
        if (super.isServerMode()) {
            synchronized (socketLock) {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                        serverSocket = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    @Override
    protected Object getServiceInfo(String serviceProperty) {
        if ("serviceLocalHost".equals(serviceProperty)) {
            if (Strings.isNull(localHost)) {
                InetAddress addr = serverSocket == null ? null : serverSocket.getInetAddress();
                localHost = addr.getHostAddress();
            }
            return localHost;
        } else if ("serviceLocalPort".equals(serviceProperty)) {
            if (localPort == 0) {
                localPort = serverSocket == null ? super.config.getInt("port") : serverSocket.getLocalPort();
            }
            return localPort;
        }
        return super.config.get(serviceProperty);
    }
    
    @Override
    public String getServiceInfo() {
        if (serverSocket == null) {
            return "SOCKET";
        } else {
            if (Strings.isNull(svcInfoText)) {
                svcInfoText = "HOST: \"" + getServiceInfo("serviceLocalHost") + ":" + getServiceInfo("serviceLocalPort") + "\"";
            }
            return svcInfoText;
        }
    }
    
}
