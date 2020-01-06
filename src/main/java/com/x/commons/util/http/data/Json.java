package com.x.commons.util.http.data;

import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.json.Jsons;
import com.x.commons.util.string.Strings;

import java.util.Iterator;
import java.util.Map;

/**
 * @Desc Json对象
 * @Date 2019-11-16 20:42
 * @Author AD
 */
public class Json implements Iterable<Map.Entry<Object, Object>> {

    private Map<Object, Object> json;

    public Json() {
        this.json = New.map();
    }

    public Json(Map map) {
        this.json = map;
    }

    public Json put(Object key, Object value) {
        json.put(key, value);
        return this;
    }

    public Json putAll(Map param) {
        if (param == null) {
            return this;
        }
        this.json.putAll(param);
        return this;
    }

    public Json putAll(Json param) {
        return putAll(param.toMap());
    }

    public Map<Object, Object> toMap() {
        return this.json;
    }

    public Object get(Object key) {
        return json.get(key);
    }

    public String toKeyValue() {
        Iterator<Map.Entry<Object, Object>> it = json.entrySet().iterator();
        SB sb = New.sb();
        while (it.hasNext()) {
            Map.Entry<Object, Object> next = it.next();
            String key = String.valueOf(next.getKey());
            String value = String.valueOf(next.getValue());
            sb.append(key).append("=").append(value).append("&");
        }
        return sb.deleteLast().get();
    }

    public String toJson() {
        return Jsons.to(json);
    }

    @Override
    public Iterator<Map.Entry<Object, Object>> iterator() {
        return json.entrySet().iterator();
    }


    /**
     * Json对象构建器
     */
    public static class Builder {

        private Map<Object, Object> json = New.map();

        public Builder of(Object key, Object value) {
            this.json.put(key, value);
            return this;
        }

        public Json build() {
            return new Json(this.json);
        }

    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }

}
