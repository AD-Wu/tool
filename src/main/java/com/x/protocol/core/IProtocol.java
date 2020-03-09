package com.x.protocol.core;

import com.x.protocol.anno.info.DataInfo;
import org.slf4j.Logger;

public interface IProtocol {
    
    String getName();
    
    Logger getLogger();
    
    DataInfo getDataConfig(Class<?> clazz);
    
    boolean isStopped();
    
    IProtocolInitializer getInitializer();
    
}
