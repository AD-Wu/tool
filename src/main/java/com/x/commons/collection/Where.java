package com.x.commons.collection;


import com.x.commons.util.string.Strings;
import com.x.protocol.annotations.XData;
import com.x.protocol.annotations.XField;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2019-11-29 23:16
 * @Author AD
 */
@XData(cache = false, doc = "查询条件数据")
public class Where implements Serializable {

    private static final long serialVersionUID = 1L;

    @XField(doc = "条件名称（属性名称）")
    private String k;

    @XField(doc = "操作方式（ 包括：>, =, <, <>, >=, <=, like ）")
    private String o;

    @XField(doc = "条件值（属性值）")
    private Object v;

    public Where() {}

    public Where(String k, String o, Object v) {
        this.k = k;
        this.o = o;
        this.v = v;
    }

    /**
     * 获取 条件名称（属性名称）
     */
    public String getK() {
        return this.k;
    }

    /**
     * 设置 条件名称（属性名称）
     */
    public void setK(String k) {
        this.k = k;
    }

    /**
     * 获取 操作方式（ 包括：>, =, <, <>, >=, <=, like ）
     */
    public String getO() {
        return this.o;
    }

    /**
     * 设置 操作方式（ 包括：>, =, <, <>, >=, <=, like ）
     */
    public void setO(String o) {
        this.o = o;
    }

    /**
     * 获取 条件值（属性值）
     */
    public Object getV() {
        return this.v;
    }

    /**
     * 设置 条件值（属性值）
     */
    public void setV(Object v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
}
