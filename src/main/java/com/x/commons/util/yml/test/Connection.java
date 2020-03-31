package com.x.commons.util.yml.test;

import com.x.commons.util.string.Strings;

/**
 * @Desc
 * @Date 2019-11-23 19:24
 * @Author AD
 */
public class Connection {
    private String driverClass;
    private String url;
    private String username;
    private String password;
    private int poolSize;
    
    public String getDriverClass() {
        return driverClass;
    }
    
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getPoolSize() {
        return poolSize;
    }
    
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}