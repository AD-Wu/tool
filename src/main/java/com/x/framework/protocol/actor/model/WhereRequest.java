package com.x.framework.protocol.actor.model;

import com.x.commons.collection.KeyValue;
import com.x.commons.collection.Where;
import com.x.protocol.annotations.XData;
import com.x.protocol.annotations.XField;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-21 22:14
 * @Author AD
 */
@XData(cache = false, doc = "查询条件请求数据")
public class WhereRequest implements Serializable {
    
    private static final long serialVersionUID = 1;
    
    // ------------------------ 变量定义 ------------------------
    @XField(doc = "查询条件")
    private Where[] wheres;
    
    @XField(doc = "排序条件")
    private KeyValue[] orders;
    
    // ------------------------ 构造方法 ------------------------
    public WhereRequest() {}
    
    public WhereRequest(Where[] wheres, KeyValue[] orders) {
        this.wheres = wheres;
        this.orders = orders;
    }
    
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 获取查询条件
     *
     * @return Where[] 查询条件
     */
    public Where[] getWheres() {
        return this.wheres;
    }
    
    /**
     * 设置查询条件
     *
     * @param wheres 查询条件
     */
    public void setWheres(Where[] wheres) {
        this.wheres = wheres;
    }
    
    /**
     * 获取排序条件
     *
     * @return KeyValue[] 排序条件
     */
    public KeyValue[] getOrders() {
        return this.orders;
    }
    
    /**
     * 设置排序条件
     *
     * @param orders 排序条件
     */
    public void setOrders(KeyValue[] orders) {
        this.orders = orders;
    }
    
}
