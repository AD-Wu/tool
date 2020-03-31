package com.x.commons.util.yml.test;

import com.x.commons.util.string.Strings;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Date 2019-11-23 19:25
 * @Author AD
 */
public class Configuration {
    private Date released;
    private String version;
    private Connection connection;
    private List<String> protocols;
    private Map<String, String> developers;
    
    public Date getReleased() {
        return released;
    }
    
    public void setReleased(Date released) {
        this.released = released;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public List<String> getProtocols() {
        return protocols;
    }
    
    public void setProtocols(List<String> protocols) {
        this.protocols = protocols;
    }
    
    public Map<String, String> getDevelopers() {
        return developers;
    }
    
    public void setDevelopers(Map<String, String> developers) {
        this.developers = developers;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}