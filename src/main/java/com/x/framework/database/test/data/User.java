package com.x.framework.database.test.data;

import com.x.commons.util.string.Strings;
import com.x.protocol.annotations.XData;
import com.x.protocol.annotations.XField;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/23 17:02
 */
@XData(cache = true, doc = "用户")
public class User implements Serializable {
    @XField(doc = "主键", pk = true)
    private String id;

    @XField(doc = "年龄")
    private int age;

    @XField(doc = "名字")
    private String name;

    @XField(doc = "生日")
    private LocalDateTime birthday;

    @XField(doc = "性别")
    private boolean sex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
