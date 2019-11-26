package com.x.commons.util.prop.test;

import com.x.commons.util.string.Strings;

/**
 * @Desc TODO
 * @Date 2019-11-23 13:49
 * @Author AD
 */
public class Connect {
    
    // ------------------------ 成员变量 ------------------------
    
    private String phone;
    
    private String email;
    
    // ------------------------ 构造方法 ------------------------
    
    public Connect() {}
    
    // ------------------------ 成员方法 ------------------------
    
    /**
     * 获取
     */
    public String getPhone() {
        return this.phone;
    }
    
    /**
     * 设置
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /**
     * 获取
     */
    public String getEmail() {
        return this.email;
    }
    
    /**
     * 设置
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    // ------------------------ 私有方法 ------------------------
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
