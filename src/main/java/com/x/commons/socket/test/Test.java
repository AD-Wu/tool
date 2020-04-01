package com.x.commons.socket.test;

import com.x.commons.socket.bean.SocketConfig;
import com.x.commons.socket.bean.XSocketSerializer;
import com.x.commons.socket.server.SocketServer;

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
    
        
    }
    
}
