package com.x.commons.util.prop.test;

import com.x.commons.util.prop.annotation.Prop;
import com.x.commons.util.prop.annotation.XValue;
import com.x.commons.util.string.Strings;

import java.util.Date;

/**
 * @Author AD
 * @Date 2019/11/21 17:49
 */
@Prop(path = "x-framework/config/value.properties", prefix = "user")
public class User {
    
    // ------------------------ 成员变量 ------------------------
    
    @XValue(key = "name")
    private String name;
    
    private int age;
    
    private Date birthday;
    
    private Connect connect;
    
    
    
    // ------------------------ 构造方法 ------------------------
    
    public User() {}
    
    // ------------------------ 成员方法 ------------------------
    
    public String getName() {
        return name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(final int age) {
        this.age = age;
    }
    
    public Date getBirthday() {
        return birthday;
    }
    
    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }
    
    /**
     * 获取
     */
    public Connect getConnect() {
        return this.connect;
    }
    
    /**
     * 设置
     */
    public void setConnect(Connect connect) {
        this.connect = connect;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
    // ------------------------ 私有方法 ------------------------
}
