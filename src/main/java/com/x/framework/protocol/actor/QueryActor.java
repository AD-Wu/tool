package com.x.framework.protocol.actor;

import com.x.framework.protocol.actor.model.CountResult;
import com.x.framework.protocol.actor.model.PageRequest;
import com.x.framework.protocol.actor.model.WhereRequest;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-22 22:43
 * @Author AD
 */
public abstract class QueryActor<T extends Serializable> extends DaoActor<T> implements IQueryActor<T> {
    // ------------------------ 变量定义 ------------------------
    
    public static final String GET = "get";
    
    public static final String COUNT = "count";
    
    public static final String LIST = "list";
    
    public static final String PAGE = "page";
    // ------------------------ 构造方法 ------------------------
    
    protected QueryActor(String name) {
        super(name, true, null);
    }
    
    // ------------------------ 方法定义 ------------------------
    
    @Override
    public T getBean(WhereRequest request) throws Exception {
        return request == null ? null : dao.getBean(request.getWheres());
    }
    
    @Override
    public CountResult getCount(WhereRequest request) throws Exception {
        return request == null ? null : new CountResult(dao.getCount(request.getWheres()));
    }
    
    @Override
    public T[] getList(WhereRequest request) throws Exception {
        return request == null ? null : dao.getList(request.getWheres(), request.getOrders());
    }
    
    @Override
    public T[] getPage(PageRequest request) throws Exception {
        return request == null ? null :
                dao.getPage(request.getPage(), request.getPageSize(), request.getWheres(), request.getOrders());
    }
    
}
