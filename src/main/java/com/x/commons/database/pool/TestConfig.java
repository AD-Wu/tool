package com.x.commons.database.pool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Desc TODO
 * @Date 2019-11-13 22:08
 * @Author AD
 */
@Configuration
@PropertySource(value = "classpath:config/config.properties",ignoreResourceNotFound = false)
public class TestConfig {

    @Value("${user}")
    private String user;

    @Value("${pwd}")
    private String pwd;

    @Override
    public String toString() {
        return "TestConfig{" +
                "user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    /**
     * 获取 @Value("${user}")
     */
    public String getUser() {
        return this.user;
    }

    /**
     * 设置 @Value("${user}")
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 获取 @Value("${pwd}")
     */
    public String getPwd() {
        return this.pwd;
    }

    /**
     * 设置 @Value("${pwd}")
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
