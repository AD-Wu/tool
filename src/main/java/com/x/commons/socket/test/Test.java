package com.x.commons.socket.test;

import com.x.commons.socket.bean.SocketConfig;
import com.x.commons.socket.bean.XSocketSerializer;
import com.x.commons.socket.server.SocketServer;

import java.net.InetAddress;
import java.net.Socket;

/**
 * @Desc TODO
 * @Date 2020-02-18 20:03
 * @Author AD
 */
public class Test {
    
    public static void main(String[] args) throws Exception {
        SocketServerListener listener = new SocketServerListener();
        XSocketSerializer serializer = new XSocketSerializer();
        SocketServer server = new SocketServer(SocketConfig.server(7777), listener,serializer);
        server.start();
        System.out.println("服务启动成功");

        Socket client = new Socket("localhost", 7777, InetAddress.getByName("localhost"), 8888);
        String s = client.getLocalAddress().toString();
        int localPort = client.getLocalPort();
        System.out.println(s+":"+localPort);
        System.out.println(client.getLocalSocketAddress().toString());

    }


    
}
