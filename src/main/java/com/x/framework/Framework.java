package com.x.framework;

import com.x.framework.config.StringManager;
import com.x.framework.database.DaoManager;
import com.x.protocol.ProtocolManager;
import com.x.protocol.config.ServiceConfig;
import com.x.protocol.core.IProtocol;
import com.x.protocol.core.IStatusNotification;
import com.x.protocol.layers.application.config.DatabaseConfig;

/**
 * @Desc
 * @Date 2019-12-05 21:51
 * @Author AD
 */
public class Framework {
    
    // ------------------------ 变量定义 ------------------------
    private String name;
    
    private IProtocol protocol;
    
    private DaoManager daoManager;
    
    private StringManager stringManager;
    
    private volatile boolean stopped;
    
    // ------------------------ 构造方法 ------------------------
    Framework() {}
    
    // ------------------------ 方法定义 ------------------------
    boolean start(ServiceConfig config, IStatusNotification status) throws Exception {
        this.name = config.getName();
        this.stringManager=new StringManager(name);
        DatabaseConfig database = config.getApplication().getDatabase();
        if(database!=null){
            this.daoManager = new DaoManager(name, database);
        }
        this.protocol = ProtocolManager.start(config, status);
        if(protocol==null){
            return false;
        }else{
            if(daoManager!=null){
                daoManager.setProtocol(protocol);
            }
            return true;
        }
    }
    
    void stop(){
        if (!this.stopped) {
            this.stopped = true;
            this.protocol.stop();
            if (this.daoManager != null) {
                this.daoManager.stop();
            }
        }
    }
    
    public String getName() {
        return name;
    }
    
    public IProtocol getProtocol() {
        return protocol;
    }
    
    public DaoManager getDaoManager() {
        return daoManager;
    }
    
    public StringManager getStringManager() {
        return stringManager;
    }
    
}
