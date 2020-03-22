package com.x.framework.protocol.serializer;

import com.x.commons.util.json.Jsons;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.DataInfo;
import com.x.protocol.core.ISerializer;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-21 22:50
 * @Author AD
 */
public class JSONSerializer implements ISerializer {
    
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
        return Jsons.toJson(data, format);
    }
    
    @Override
    public Serializable deserialize(DataInfo dataInfo, Serializable data) throws Exception {
        return data != null && data instanceof String ?
                (Serializable) Jsons.fromJson((String) data, dataInfo.getDataClass(), format) : data;
    }
    
}
