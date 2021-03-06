package com.x.commons.collection;

import com.x.commons.util.string.Strings;
import com.x.protocol.annotations.XData;
import com.x.protocol.annotations.XField;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2019-12-08 13:09
 * @Author AD
 */
@XData(doc = "键值对数据", cache = false)
public class KeyValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @XField(doc = "数据键")
    private String k;

    @XField(doc = "数据值")
    private Object v;

    public KeyValue() {
    }

    public KeyValue(String key, Object value) {
        this.k = key;
        this.v = value;
    }

    public String getK() {
        return this.k;
    }

    public void setK(String key) {
        this.k = key;
    }

    public Object getV() {
        return this.v;
    }

    public void setV(Object value) {
        this.v = value;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
}
