package com.x.commons.socket.test;

import com.x.commons.socket.bean.SocketConfig;
import com.x.commons.socket.bean.XSocketSerializer;
import com.x.commons.socket.server.SocketServer;

import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @Desc TODO
 * @Date 2020-02-18 20:03
 * @Author AD
 */
public class Test {
    
    public static void main(String[] args) throws Exception {
        domainTest();
        
    }
    
    private static void domainTest() throws Exception {
        InetAddress addr = InetAddress.getByName("www.baidu.com");
        String hostAddress = addr.getHostAddress();
        System.out.println(hostAddress);
        
        InetAddress addr1 = InetAddress.getByName("baidu.com");
        String hostAddress1 = addr.getHostAddress();
        System.out.println(hostAddress1);
    
        
        Socket socket = new Socket(InetAddress.getByName("www.baidu.com"),443);
        SocketAddress remote = socket.getRemoteSocketAddress();
        System.out.println(remote);
    }
    
    private static void mySocketTest() throws Exception {
        SocketServerListener listener = new SocketServerListener();
        XSocketSerializer serializer = new XSocketSerializer();
        SocketServer server = new SocketServer(SocketConfig.server(7777), listener, serializer);
        server.start();
        System.out.println("服务启动成功");
        
        Socket client = new Socket("localhost", 7777, InetAddress.getByName("localhost"), 8888);
        String s = client.getLocalAddress().toString();
        int localPort = client.getLocalPort();
        System.out.println(s + ":" + localPort);
        System.out.println(client.getLocalSocketAddress().toString());
    }
    
}
