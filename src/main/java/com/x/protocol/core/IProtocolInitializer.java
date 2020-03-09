package com.x.protocol.core;

import com.x.commons.collection.DataSet;
import com.x.protocol.network.core.NetworkConfig;

/**
 * @Desc
 * @Date 2020-03-09 00:45
 * @Author AD
 */
public interface IProtocolInitializer {

    boolean onChannelPrepare(IProtocol prtc, IChannel ch, NetworkConfig cfg);

    void onChannelStart(IProtocol prtc, IChannel ch);

    void onChannelStop(IProtocol prtc, IChannel ch);

    boolean onClientConnected(IProtocol prtc, ChannelInfo info);

    void onClientDisconnected(IProtocol prtc, ChannelInfo info);

    boolean onClientPrepare(IProtocol prtc, IChannel ch, DataSet data);

    boolean onRemoteConnected(IProtocol prtc, ChannelInfo info);

    void onRemoteDisconnected(IProtocol prtc, ChannelInfo info);

    void onSecurityDefense(IProtocol prtc, ChannelInfo info);

    void onServiceStart(IProtocol prtc, DataSet data);

    void onServiceStopping(IProtocol prtc);

    void onServiceStop(IProtocol prtc);

    void onSessionCreated(IProtocol prtc, ISession session);

    void onSessionDestroyed(IProtocol prtc, ISession session);
}
