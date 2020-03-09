package com.x.protocol.layers.application.config;

import com.x.commons.util.string.Strings;

import java.io.Serializable;

/**
 * @Desc TODO
 * @Date 2020-03-08 20:00
 * @Author AD
 */
public class ActorSetting implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private boolean disableDoc;
    
    private String defaultActor;
    
    // ------------------------ 构造方法 ------------------------
    public ActorSetting() {}
    // ------------------------ 方法定义 ------------------------
    
    public boolean isDisableDoc() {
        return disableDoc;
    }
    
    public void setDisableDoc(boolean disableDoc) {
        this.disableDoc = disableDoc;
    }
    
    public String getDefaultActor() {
        return defaultActor;
    }
    
    public void setDefaultActor(String defaultActor) {
        this.defaultActor = defaultActor;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
