package com.x.commons.util.teamwork.test;

import com.x.commons.util.string.Strings;
import lombok.Data;

/**
 * @Desc TODO
 * @Date 2019-11-30 23:51
 * @Author AD
 */
@Data
public class User {
    
    private int id;
    
    private String name;
    
    private int age;
    
    private boolean sex;
    
    private String birthday;
    
    @Override
    public String toString() {
        return Strings.simpleToString(this);
    }
    
}
