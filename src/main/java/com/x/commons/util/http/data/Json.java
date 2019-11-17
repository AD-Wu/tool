package com.x.commons.util.http.data;

import com.x.commons.util.bean.New;
import com.x.commons.util.json.Jsons;

import java.util.Map;

/**
 * @Desc TODO Json对象
 * @Date 2019-11-16 20:42
 * @Author AD
 */
public class Json {

    private Map<Object, Object> json;

    public Json() {
        this.json = New.map();
    }

    public Json(Map<Object, Object> map) {
        this.json = map;
    }

    public void put(Object key, Object value) {
        json.put(key, value);
    }

    public Object get(Object key) {
        return json.get(key);
    }

    @Override
    public String toString() {
        return Jsons.to(json);
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

}
