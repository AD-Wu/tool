package com.x.commons.socket;

import com.x.commons.socket.server.SocketManager;

/**
 * @Desc TODO
 * @Date 2020-02-18 20:03
 * @Author AD
 */
public class Test {
    
    public static void main(String[] args) throws Exception {
        SocketManager.SERVER.start(1111);
    }
    
}
