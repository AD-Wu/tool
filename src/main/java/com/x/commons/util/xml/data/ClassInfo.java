package com.x.commons.util.xml.data;

import com.x.commons.util.string.Strings;

/**
 * @Desc 类信息对象
 * @Date 2019-11-24 15:58
 * @Author AD
 */
public class ClassInfo {
    
    // ------------------------ 成员变量 ------------------------
    
    /**
     * 类名
     */
    private String name;
    
    /**
     * 类对象
     */
    private Class<?> type;
    
    // ------------------------ 构造方法 ------------------------
    
    public ClassInfo() {}
    
    // ------------------------ 成员方法 ------------------------
    
    /**
     * 获取  类名
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * 设置  类名
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取  类对象
     */
    public Class<?> getType() {
        return this.type;
    }
    
    /**
     * 设置  类对象
     */
    public void setType(Class<?> type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
