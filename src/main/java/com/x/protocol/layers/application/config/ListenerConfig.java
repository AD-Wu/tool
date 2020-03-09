package com.x.protocol.layers.application.config;

import com.x.commons.util.string.Strings;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-08 19:59
 * @Author AD
 */
public class ListenerConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private String base;
    
    private String pkgName;
    
    private String remark;
    
    // ------------------------ 构造方法 ------------------------
    public ListenerConfig() {}
    // ------------------------ 方法定义 ------------------------
    
    public String getBase() {
        return base;
    }
    
    public void setBase(String base) {
        this.base = base;
    }
    
    public String getPkgName() {
        return pkgName;
    }
    
    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
