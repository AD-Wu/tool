package com.x.commons.util.http.data;

import com.x.commons.util.bean.New;

import java.util.Iterator;
import java.util.Map;

/**
 * @Desc TODO
 * @Date 2020-01-29 20:32
 * @Author AD
 */
public final class HttpParam implements Iterable<Map.Entry<ValueType, Object>> {
    // ------------------------ 变量定义 ------------------------
    
    private final Map<ValueType, Object> params;
    
    // ------------------------ 构造方法 ------------------------
    
    public HttpParam() {
        this.params = New.linkedMap();
    }
    
    // ------------------------ 方法定义 ------------------------
    public HttpParam add(Object key, Object value) {
        ValueType<Object> type = new ValueType<>(ValueType.OBJECT, key);
        params.put(type,value);
        return this;
    }
    
    public <T> HttpParam add(ValueType<T> type, Object key, T value) {
        params.put(new ValueType<>(type, key),value);
        return this;
    }
    
    public String toJson() {
        return getJson().toJson();
    }
    
    public String toKeyValue() {
        return getJson().toKeyValue();
    }
    
    @Override
    public Iterator<Map.Entry<ValueType, Object>> iterator() {
        return params.entrySet().iterator();
    }
    
    // ------------------------ 私有方法 ------------------------
    
    private Json getJson() {
        Iterator<Map.Entry<ValueType, Object>> it = params.entrySet().iterator();
        Json json = new Json();
        while (it.hasNext()) {
            Map.Entry<ValueType, Object> next = it.next();
            ValueType type = next.getKey();
            Object key = type.getKey();
            json.put(key, next.getValue());
        }
        return json;
    }
   
}
