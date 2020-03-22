package com.x.framework.protocol.actor.model;

import com.x.commons.collection.KeyValue;
import com.x.commons.collection.Where;
import com.x.protocol.annotations.XData;
import com.x.protocol.annotations.XField;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-21 22:18
 * @Author AD
 */
@XData(cache = false, doc = "更新请求数据")
public class UpdateRequest implements Serializable {
    
    private static final long serialVersionUID = 1;
    
    // ------------------------ 变量定义 ------------------------
    @XField(doc = "更新数据值")
    private KeyValue[] updates;
    
    @XField(doc = "更新条件")
    private Where[] wheres;
    
    // ------------------------ 构造方法 ------------------------
    public UpdateRequest() {}
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 获取更新数据值
     *
     * @return KeyValue[] 更新数据值
     */
    public KeyValue[] getUpdates() {
        return this.updates;
    }
    
    /**
     * 设置更新数据值
     *
     * @param updates 更新数据值
     */
    public void setUpdates(KeyValue[] updates) {
        this.updates = updates;
    }
    
    /**
     * 获取更新条件
     *
     * @return Where[] 更新条件
     */
    public Where[] getWheres() {
        return this.wheres;
    }
    
    /**
     * 设置更新条件
     *
     * @param wheres 更新条件
     */
    public void setWheres(Where[] wheres) {
        this.wheres = wheres;
    }
    
}
