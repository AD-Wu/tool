package com.x.commons.socket.server;

import com.x.commons.interfaces.IMapFactory;
import com.x.commons.socket.config.ServerConfig;

/**
 * @Desc TODO
 * @Date 2019-10-28 23:39
 * @Author AD
 */
class SocketServerFactory implements IMapFactory<ISocket, ServerConfig> {

    @Override
    public ISocket get(ServerConfig config) throws Exception {
        return new SocketServer(config);
    }

}
