package com.x.framework.protocol.actor;

import com.x.framework.protocol.actor.model.CountResult;
import com.x.framework.protocol.actor.model.PageRequest;
import com.x.framework.protocol.actor.model.WhereRequest;
import com.x.protocol.annotations.XAuto;
import com.x.protocol.annotations.XRecv;

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
    @XRecv(ctrl = GET, doc = "获取数据", req = WhereRequest.class, resp = XAuto.class)
    public T getBean(WhereRequest request) throws Exception {
        return request == null ? null : dao.getBean(request.getWheres());
    }
    
    @Override
    @XRecv(ctrl = COUNT, doc = "获取数据总数", req = WhereRequest.class, resp = CountResult.class)
    public CountResult getCount(WhereRequest request) throws Exception {
        return request == null ? null : new CountResult(dao.getCount(request.getWheres()));
    }
    
    @Override
    @XRecv(ctrl = LIST, doc = "获取数据列表", req = WhereRequest.class, resp = XAuto.class)
    public T[] getList(WhereRequest request) throws Exception {
        return request == null ? null : dao.getList(request.getWheres(), request.getOrders());
    }
    
    @Override
    @XRecv(ctrl = PAGE, doc = "获取分页数据", req = WhereRequest.class, resp = XAuto.class)
    public T[] getPage(PageRequest request) throws Exception {
        return request ==null ? null :
                dao.getPage(request.getPage(), request.getPageSize(), request.getWheres(), request.getOrders());
    }
    
}
