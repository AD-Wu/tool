package com.x.commons.socket.server;

import com.x.commons.interfaces.IIFactory;
import com.x.commons.socket.config.ServerConfig;

/**
 * @Desc TODO
 * @Date 2019-10-28 23:39
 * @Author AD
 */
class SocketServerFactory implements IIFactory<ISocket, ServerConfig> {

    @Override
    public ISocket get(ServerConfig config) throws Exception {
        return new SocketServer(config);
    }

}
