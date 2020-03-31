package com.x.commons.util.yml.test;

import com.x.commons.util.string.Strings;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @Desc
 * @Date 2019-11-23 19:44
 * @Author AD
 */
public class Config {
    
    private Date released;
    
    private String version;
    
    private Connection connection;
    
    private double[] ints;
    
    private Set<String> protocols;
    
    private Map<String, String> developers;
    
    private String c_text;
    
    private String d_text;
    
    public Config() {
    }
    
    public static void main(String[] args) {
        String s = new Config().toString();
        System.out.println(s);
    }

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

    public double[] getInts() {
        return ints;
    }

    public void setInts(double[] ints) {
        this.ints = ints;
    }

    public Set<String> getProtocols() {
        return protocols;
    }

    public void setProtocols(Set<String> protocols) {
        this.protocols = protocols;
    }

    public Map<String, String> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Map<String, String> developers) {
        this.developers = developers;
    }

    public String getC_text() {
        return c_text;
    }

    public void setC_text(String c_text) {
        this.c_text = c_text;
    }

    public String getD_text() {
        return d_text;
    }

    public void setD_text(String d_text) {
        this.d_text = d_text;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
