package com.x.commons.socket.client;

import com.x.commons.interfaces.IIFactory;
import com.x.commons.socket.config.ClientConfig;
import com.x.commons.socket.server.ISocket;

/**
 * @Desc TODO
 * @Date 2019-11-05 20:11
 * @Author AD
 */
public class SocketClientFactory implements IIFactory<ISocket, ClientConfig> {

    @Override
    public ISocket get(ClientConfig config) throws Exception {
        return new SocketClient(config);
    }

}
