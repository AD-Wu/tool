package com.x.protocol.network;

import com.x.protocol.network.core.NetworkConfig;
import com.x.protocol.network.core.NetworkServiceType;
import com.x.protocol.network.factory.custom.CustomService;
import com.x.protocol.network.factory.http.HttpService;
import com.x.protocol.network.factory.serial.SerialService;
import com.x.protocol.network.factory.socket.SocketService;
import com.x.protocol.network.interfaces.INetworkNotification;
import com.x.protocol.network.interfaces.INetworkService;

/**
 * @Desc
 * @Date 2020-03-08 13:28
 * @Author AD
 */
public final class NetworkManager {

    private NetworkManager() {}

    public static INetworkService start(NetworkConfig config, INetworkNotification notification) {
        String type = config.getType();
        INetworkService service = null;
        switch (type) {
            case NetworkServiceType.SOCKET:
                service = new SocketService(notification);
            case NetworkServiceType.HTTP:
                service = new HttpService(notification);
            case NetworkServiceType.SERIAL:
                service = new SerialService(notification);
            case NetworkServiceType.CUSTOM:
                service = new CustomService(notification);
            default:
                break;
        }
        return (service != null && service.start(config)) ? service : null;
    }

}
