package com.x.protocol.layers.session.config;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-08 22:14
 * @Author AD
 */
public class SessionConfig implements Serializable {
    
    private static final long serialVersionUID = 4972072613875901536L;
    
    // ------------------------ 变量定义 ------------------------
    private LoginConfig login;
    
    private ConnectionConfig connection;
    
    // ------------------------ 构造方法 ------------------------
    public SessionConfig() {}
    // ------------------------ 方法定义 ------------------------
    
    public LoginConfig getLogin() {
        return login;
    }
    
    public void setLogin(LoginConfig login) {
        this.login = login;
    }
    
    public ConnectionConfig getConnection() {
        return connection;
    }
    
    public void setConnection(ConnectionConfig connection) {
        this.connection = connection;
    }
    
}
