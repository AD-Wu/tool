package com.x.protocol.layers.session.config;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-08 22:14
 * @Author AD
 */
public class LoginConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private boolean loginMode = false;
    
    private boolean multiSerializer = false;
    
    private String commands;
    
    // ------------------------ 构造方法 ------------------------
    public LoginConfig() {}
    // ------------------------ 方法定义 ------------------------
    
    public boolean isLoginMode() {
        return loginMode;
    }
    
    public void setLoginMode(boolean loginMode) {
        this.loginMode = loginMode;
    }
    
    public boolean isMultiSerializer() {
        return multiSerializer;
    }
    
    public void setMultiSerializer(boolean multiSerializer) {
        this.multiSerializer = multiSerializer;
    }
    
    public String getCommands() {
        return commands;
    }
    
    public void setCommands(String commands) {
        this.commands = commands;
    }
    
}
