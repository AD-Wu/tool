package com.x.commons.socket;

import com.x.commons.socket.bean.XSocketSerializer;
import com.x.commons.socket.server.SocketServer;
import com.x.commons.socket.server.SocketServerConfig;
import com.x.commons.socket.server.SocketServerListener;

/**
 * @Desc TODO
 * @Date 2020-02-18 20:03
 * @Author AD
 */
public class Test {
    
    public static void main(String[] args) throws Exception {
        SocketServerListener listener = new SocketServerListener();
        XSocketSerializer serializer = new XSocketSerializer();
        SocketServer server = new SocketServer(new SocketServerConfig(7777), listener,serializer);
        server.start();
        System.out.println("服务启动成功");
        
    
    }
    
}
