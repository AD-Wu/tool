package com.x.framework.protocol.actor.model;

import com.x.protocol.annotations.XData;
import com.x.protocol.annotations.XField;

import java.io.Serializable;

/**
 * @Desc TODO
 * @Date 2020-03-21 22:25
 * @Author AD
 */
@XData(cache = false,doc = "数量结果数据")
public class CountResult implements Serializable {
    
    private static final long serialVersionUID = 1;
    
    // ------------------------ 变量定义 ------------------------
    @XField(doc = "数据条数或受影响条数")
    private int count=-1;
    // ------------------------ 构造方法 ------------------------
    public CountResult(int count) {
        this.count = count;
    }
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 获取数据条数或受影响条数
     * @return int 数据条数或受影响条数
     */
    public int getCount() {
        return this.count;
    }
    
    /**
     * 设置数据条数或受影响条数
     * @param count 数据条数或受影响条数
     */
    public void setCount(int count) {
        this.count = count;
    }
    
}
