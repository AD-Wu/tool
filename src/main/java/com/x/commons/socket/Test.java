package com.x.commons.socket;

import com.x.commons.socket.client.SocketClient;
import com.x.commons.socket.client.SocketClientConfig;
import com.x.commons.socket.client.SocketClientListener;

/**
 * @Desc TODO
 * @Date 2020-02-18 20:03
 * @Author AD
 */
public class Test {
    
    public static void main(String[] args) throws Exception {
        // SocketServerListener listener = new SocketServerListener();
        // XSocketSerializer serializer = new XSocketSerializer();
        // SocketServer server = new SocketServer(new SocketServerConfig(7777), listener,serializer);
        // server.start();
        // System.out.println("服务启动成功");
    
        SocketClientListener listener = new SocketClientListener();
        SocketClient client = new SocketClient(SocketClientConfig.getLocal(7777), listener);
        client.start();
        System.out.println("异步");
    }
    
}
