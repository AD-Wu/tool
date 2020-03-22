package com.x.framework.protocol.actor;

import com.x.framework.protocol.actor.model.CountResult;
import com.x.framework.protocol.actor.model.UpdateRequest;
import com.x.framework.protocol.actor.model.WhereRequest;
import com.x.protocol.annotations.XAuto;
import com.x.protocol.annotations.XRecv;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-22 23:06
 * @Author AD
 */
public abstract class CrudActor<T extends Serializable> extends QueryActor<T> implements ICrudActor<T> {
    
    // ------------------------ 变量定义 ------------------------
    
    public static final String ADD = "add";
    
    public static final String ADD_ALL = "add-all";
    
    public static final String PUT = "put";
    
    public static final String PUT_ALL = "put-all";
    
    public static final String DELETE = "delete";
    
    public static final String EDIT = "edit";
    
    public static final String UPDATE = "update";
    
    // ------------------------ 构造方法 ------------------------
    
    protected CrudActor(String name) {
        super(name);
    }
    
    // ------------------------ 方法定义 ------------------------
    
    @Override
    @XRecv(ctrl = ADD, doc = "增加数据", req = XAuto.class, resp = XAuto.class)
    public T add(T bean) throws Exception {
        return bean == null ? null : dao.add(bean);
    }
    
    @Override
    @XRecv(ctrl = ADD_ALL, doc = "批量增加数据", req = XAuto.class, resp = CountResult.class)
    public CountResult addAll(T[] beans) throws Exception {
        return beans == null ? null : new CountResult(dao.addAll(beans).length);
    }
    
    @Override
    @XRecv(ctrl = PUT, doc = "设置数据(不存在则增加,存在则替换)",req = XAuto.class, resp = XAuto.class)
    public T put(T bean) throws Exception {
        return bean == null ? null : dao.put(bean);
    }
    
    @Override
    @XRecv(ctrl = PUT_ALL, doc = "批量设置数据(不存在则增加,存在则替换)",req = XAuto.class, resp = CountResult.class)
    public CountResult putAll(T[] beans) throws Exception {
        return beans == null ? null : new CountResult(dao.putAll(beans).length);
    }
    
    @Override
    @XRecv(ctrl = DELETE, doc = "删除数据", req = XAuto.class, resp = CountResult.class)
    public CountResult delete(WhereRequest request) throws Exception {
        return request == null ? null : new CountResult(dao.delete(request.getWheres()));
    }
    
    @Override
    @XRecv(ctrl = EDIT, doc = "编辑数据", req = XAuto.class, resp = XAuto.class)
    public T edit(T bean) throws Exception {
        return bean == null ? null : dao.edit(bean) > 0 ? bean : null;
    }
    
    @Override
    @XRecv(ctrl = UPDATE, doc = "更新数据", req = UpdateRequest.class, resp = CountResult.class)
    public CountResult update(UpdateRequest request) throws Exception {
        return request==null?null:new CountResult(dao.update(request.getUpdates(), request.getWheres()));
    }
    
}
