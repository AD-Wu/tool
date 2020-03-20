package com.x.commons.collection;

import com.x.commons.util.string.Strings;
import com.x.protocol.annotations.XData;
import com.x.protocol.annotations.XField;

import java.io.Serializable;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 14:09
 */
@XData(cache = false, doc = "数值对数据")
public class ShortValue implements Serializable {
    private static final long serialVersionUID = 1L;

    @XField(doc = "数据键")
    private short k;

    @XField(doc = "数据值")
    private Object v;

    public ShortValue(short k, Object v) {
        this.k = k;
        this.v = v;
    }

    public short getK() {
        return k;
    }

    public void setK(short k) {
        this.k = k;
    }

    public Object getV() {
        return v;
    }

    public void setV(Object v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
}
