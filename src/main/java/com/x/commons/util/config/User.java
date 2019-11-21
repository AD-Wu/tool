package com.x.commons.util.config;

import com.x.commons.util.config.annotation.Config;
import com.x.commons.util.config.annotation.XValue;
import com.x.commons.util.string.Strings;

/**
 * @Author AD
 * @Date 2019/11/21 17:49
 */
@Config(path = "config/value.properties",prefix = "user")
public class User {

    @XValue(key = "name")
    private String name;

    private String age;

    private String birthday;

    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public String getAge() {
        return age;
    }
    public void setAge(final String age) {
        this.age = age;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(final String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }

}
