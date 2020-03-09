package com.x.protocol.core;

import com.x.commons.events.IListener;

/**
 * @Desc TODO
 * @Date 2020-03-08 20:34
 * @Author AD
 */
public interface IProtocolListener extends IListener {
    EventInfo[] getEventInfos();
}
