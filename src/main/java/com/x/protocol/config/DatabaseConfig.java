package com.x.protocol.config;

import java.io.Serializable;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/19 17:14
 */
public class DatabaseConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;

    private String driverClass;

    private String url;

    private String user;

    private String password;

    private String initialSize;

    private String maxActive;

    private String maxIdle;

    private String minIdle;

    private String maxWait;

    private String minEvictableIdleTimeMillis;

    private String numTestsPerEvictionRun;

    private String testOnBorrow;

    private String testOnReturn;

    private String testWhileIdle;

    private String timeBetweenEvictionRunsMillis;

    private String validateQuery;

    public DatabaseConfig() {
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDriverClass() {
        return this.driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getURL() {
        return this.url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInitialSize() {
        return this.initialSize;
    }

    public void setInitialSize(String initSize) {
        this.initialSize = initSize;
    }

    public String getMaxActive() {
        return this.maxActive;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }

    public String getMaxIdle() {
        return this.maxIdle;
    }

    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getMinIdle() {
        return this.minIdle;
    }

    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    public String getMaxWait() {
        return this.maxWait;
    }

    public void setMaxWait(String maxWait) {
        this.maxWait = maxWait;
    }

    public String getMinEvictableIdleTimeMillis() {
        return this.minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getNumTestsPerEvictionRun() {
        return this.numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(String numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public String getTestOnBorrow() {
        return this.testOnBorrow;
    }

    public void setTestOnBorrow(String testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public String getTestOnReturn() {
        return this.testOnReturn;
    }

    public void setTestOnReturn(String testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public String getTestWhileIdle() {
        return this.testWhileIdle;
    }

    public void setTestWhileIdle(String testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public String getTimeBetweenEvictionRunsMillis() {
        return this.timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(String timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public String getValidateQuery() {
        return this.validateQuery;
    }

    public void setValidateQuery(String validateQuery) {
        this.validateQuery = validateQuery;
    }
}
