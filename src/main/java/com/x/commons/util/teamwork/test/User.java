package com.x.commons.util.teamwork.test;

import com.x.commons.interfaces.ICallback;
import com.x.commons.util.date.DateTimes;
import com.x.commons.util.string.Strings;
import lombok.Data;

/**
 * @Desc TODO
 * @Date 2019-11-30 23:51
 * @Author AD
 */
@Data
public class User<T> {
    
    private static int counter = 0;
    
    private int id;
    
    private String name;
    
    private int age;
    
    private boolean sex;
    
    private String birthday;
    
    private final ICallback<T> callback;
    
    public User(ICallback<T> callback) {
        this.id = ++counter;
        this.name = "AD";
        this.age = 28;
        this.sex = true;
        this.birthday = DateTimes.now();
        this.callback = callback;
    }
    
    public ICallback<T> getCallback() {
        return callback;
    }
    
    @Override
    public String toString() {
        return Strings.simpleToString(this);
    }
    
}
