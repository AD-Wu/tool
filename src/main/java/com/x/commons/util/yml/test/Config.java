package com.x.commons.util.yml.test;

import com.google.common.base.MoreObjects;
import com.x.commons.util.string.Strings;
import lombok.Data;

import java.util.*;

/**
 * @Desc TODO
 * @Date 2019-11-23 19:44
 * @Author AD
 */
@Data
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
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
