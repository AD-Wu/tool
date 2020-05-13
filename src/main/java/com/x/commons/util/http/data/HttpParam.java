package com.x.commons.util.http.data;

import com.x.commons.util.bean.New;

import java.io.File;
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
        Class<?> clazz = value.getClass();
        if (clazz.equals(byte[].class)) {
            return add(ValueType.BYTES, key, (byte[]) value);
        } else if (clazz.equals(File.class)) {
            return add(ValueType.FILE, key, (File) value);
        } else {
            return add(ValueType.OBJECT, key, value);
        }
    }

    public <T> HttpParam add(ValueType<T> type, Object key, T value) {
        params.put(new ValueType<>(type, key), value);
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
