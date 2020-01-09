package com.x.commons.socket.server;

import com.x.commons.interfaces.IMapFactory;
import com.x.commons.socket.client.SocketClientFactory;
import com.x.commons.socket.config.ClientConfig;
import com.x.commons.socket.config.ServerConfig;
import com.x.commons.util.log.Logs;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc TODO socket服务端管理者
 * @Date 2019-10-19 22:12
 * @Author AD
 */
public enum SocketManager {
    SERVER {
        @Override
        public ISocket start(int port, String... ip) throws Exception {
            while (serverMap.containsKey(port)) {
                Logs.get(this.getClass()).warn("The port is using,will start with the new port:{}", ++port);
            }
            ISocket server = serverFactory.get(new ServerConfig(port));
            serverMap.put(port, server);
            server.start();
            return server;
        }

        @Override
        public void stop(int port) throws Exception{
            // ifPresent只有在取得server时才会执行
            Optional.ofNullable(serverMap.get(port)).ifPresent(server -> server.stop());
        }

    },
    CLIENT {
        @Override
        public ISocket start(int port, String... ip) throws Exception {
            ISocket client = clientFactory.get(new ClientConfig(ip[0], port));
            client.start();
            return client;
        }

        @Override
        public void stop(int port) throws Exception {
        }
    };

    private static Map<Integer, ISocket> serverMap = new ConcurrentHashMap<>();

    private static IMapFactory<ISocket, ServerConfig> serverFactory = new SocketServerFactory();

    private static IMapFactory<ISocket, ClientConfig> clientFactory = new SocketClientFactory();

    public abstract ISocket start(int port, String... ip) throws Exception;

    public abstract void stop(int port) throws Exception;



    public static void main(String[] args) throws Exception {
    }

}
