package com.x.framework.protocol.actor.model;

import com.x.commons.collection.KeyValue;
import com.x.commons.collection.Where;
import com.x.protocol.annotations.XData;
import com.x.protocol.annotations.XField;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-21 22:21
 * @Author AD
 */
@XData(cache = false, doc = "分页请求数据")
public class PageRequest implements Serializable {
    
    private static final long serialVersionUID = 1;
    
    // ------------------------ 变量定义 ------------------------
    @XField(doc = "当前页")
    private int page;
    
    @XField(doc = "每页显示数据条数")
    private int pageSize;
    
    @XField(doc = "查询条件")
    private Where[] wheres;
    
    @XField(doc = "排序条件")
    private KeyValue[] orders;
    
    // ------------------------ 构造方法 ------------------------
    public PageRequest() {}
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 获取当前页
     *
     * @return int 当前页
     */
    public int getPage() {
        return this.page;
    }
    
    /**
     * 设置当前页
     *
     * @param page 当前页
     */
    public void setPage(int page) {
        this.page = page;
    }
    
    /**
     * 获取每页显示数据条数
     *
     * @return int 每页显示数据条数
     */
    public int getPageSize() {
        return this.pageSize;
    }
    
    /**
     * 设置每页显示数据条数
     *
     * @param pageSize 每页显示数据条数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
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
