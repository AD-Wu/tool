package com.x.framework.protocol.serializer;

import com.x.commons.util.string.Strings;
import com.x.commons.util.xml.Xmls;
import com.x.protocol.core.DataInfo;
import com.x.protocol.core.ISerializer;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-21 22:58
 * @Author AD
 */
public class XMLSerializer implements ISerializer {
    
    // ------------------------ 变量定义 ------------------------
    
    private static final String DATE_FORMAT = "format";
    
    private String format;
    
    // ------------------------ 方法定义 ------------------------
    
    @Override
    public void add(String key, Object value) {
        if (DATE_FORMAT.equals(key)) {
            this.format = Strings.of(value);
        }
    }
    
    @Override
    public Serializable serialize(DataInfo dataInfo, Serializable data) throws Exception {
        return Xmls.toXML(data, null, null, format);
    }
    
    @Override
    public Serializable deserialize(DataInfo dataInfo, Serializable data) throws Exception {
        return (Serializable) Xmls.fromXML((String) data, null, null, format);
    }
    
}
