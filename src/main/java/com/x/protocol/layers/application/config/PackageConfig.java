package com.x.protocol.layers.application.config;

import com.x.commons.util.string.Strings;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-08 19:58
 * @Author AD
 */
public class PackageConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private String base;
    
    private String name;
    
    private String remark;
    
    // ------------------------ 构造方法 ------------------------
    public PackageConfig() {}
    // ------------------------ 方法定义 ------------------------
    
    public String getBase() {
        return base;
    }
    
    public void setBase(String base) {
        this.base = base;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
