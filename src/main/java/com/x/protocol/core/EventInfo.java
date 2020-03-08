package com.x.protocol.core;

import com.x.commons.util.string.Strings;

/**
 * @Desc
 * @Date 2020-03-08 20:35
 * @Author AD
 */
public class EventInfo {
    
    // ------------------------ 变量定义 ------------------------
    private final String type;
    
    private final Object[] params;
    
    // ------------------------ 构造方法 ------------------------
    public EventInfo(String type, Object... params) {
        this.type = type;
        this.params = params;
    }
    // ------------------------ 方法定义 ------------------------
    
    public String getType() {
        return type;
    }
    
    public Object[] getParams() {
        return params;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
