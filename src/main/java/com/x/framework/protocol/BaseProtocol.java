package com.x.framework.protocol;

import com.x.framework.Framework;
import com.x.framework.FrameworkManager;
import com.x.framework.config.StringManager;
import com.x.framework.database.IDaoManager;
import com.x.protocol.core.IProtocol;
import org.slf4j.Logger;

/**
 * @Desc
 * @Date 2020-03-21 21:59
 * @Author AD
 */
public class BaseProtocol {
    
    protected final IProtocol protocol;
    
    protected final IDaoManager daoManager;
    
    protected final StringManager stringManager;
    
    protected final Logger logger;
    
    protected BaseProtocol(String name) {
        Framework framework = FrameworkManager.getFramework(name);
        if (FrameworkManager.getFramework(name) != null) {
            this.protocol = framework.getProtocol();
            this.daoManager = framework.getDaoManager();
            this.stringManager = framework.getStringManager();
            this.logger = this.protocol.getLogger();
        } else {
            this.protocol = null;
            this.daoManager = null;
            this.stringManager = null;
            this.logger = null;
        }
    }
    
}
