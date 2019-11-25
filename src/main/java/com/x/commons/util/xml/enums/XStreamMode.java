package com.x.commons.util.xml.enums;

import com.thoughtworks.xstream.XStream;

/**
 * @Desc XStream mode 枚举
 * @Date 2019-11-24 17:17
 * @Author AD
 */
public enum XStreamMode {
    
    NoReferences(1001),
    IDReferences(1002),
    XpathRelativeReferences(1003),
    Xpath_AbsoluterReferences(1004);
    
    private final int code;
    
    private XStreamMode(int code) {
        this.code = code;
    }
    
    public int code() {
        return code;
    }
}
