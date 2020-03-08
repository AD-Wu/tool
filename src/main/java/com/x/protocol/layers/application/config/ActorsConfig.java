package com.x.protocol.layers.application.config;

import com.x.commons.util.string.Strings;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc TODO
 * @Date 2020-03-08 20:03
 * @Author AD
 */
public class ActorsConfig implements Serializable {
    
    private static final long serialVersionUID = -8131330254034318401L;
    
    // ------------------------ 变量定义 ------------------------
    private ActorSetting setting;
    
    private List<PackageConfig> packages;
    
    // ------------------------ 构造方法 ------------------------
    public ActorsConfig() {}
    // ------------------------ 方法定义 ------------------------
    
    public ActorSetting getSetting() {
        return setting;
    }
    
    public void setSetting(ActorSetting setting) {
        this.setting = setting;
    }
    
    public List<PackageConfig> getPackages() {
        return packages;
    }
    
    public void setPackages(List<PackageConfig> packages) {
        this.packages = packages;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
