package com.x.commons.collection;

import com.x.protocol.annotations.coreold.XField;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2019-11-29 23:16
 * @Author AD
 */
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
    
}
