package com.x.framework.protocol.actor;

import com.x.framework.protocol.actor.model.CountResult;
import com.x.framework.protocol.actor.model.UpdateRequest;
import com.x.framework.protocol.actor.model.WhereRequest;
import com.x.protocol.annotations.XAuto;
import com.x.protocol.annotations.XRecv;

import java.io.Serializable;

import static com.x.framework.protocol.actor.CrudActor.*;

/**
 * @Desc
 * @Date 2020-03-21 23:51
 * @Author AD
 */
public interface ICrudActor<T extends Serializable> extends IQueryActor<T> {
    
    @XRecv(ctrl = ADD, doc = "增加数据", req = XAuto.class, resp = XAuto.class)
    T add(T bean) throws Exception;
    
    @XRecv(ctrl = ADD_ALL, doc = "批量增加数据", req = XAuto.class, resp = CountResult.class)
    CountResult addAll(T[] beans) throws Exception;
    
    @XRecv(ctrl = PUT, doc = "设置数据(不存在则增加,存在则替换)",req = XAuto.class, resp = XAuto.class)
    T put(T bean) throws Exception;
    
    @XRecv(ctrl = PUT_ALL, doc = "批量设置数据(不存在则增加,存在则替换)",req = XAuto.class, resp = CountResult.class)
    CountResult putAll(T[] beans) throws Exception;
    
    @XRecv(ctrl = DELETE, doc = "删除数据", req = XAuto.class, resp = CountResult.class)
    CountResult delete(WhereRequest request) throws Exception;
    
    @XRecv(ctrl = EDIT, doc = "编辑数据", req = XAuto.class, resp = XAuto.class)
    T edit(T bean) throws Exception;
    
    @XRecv(ctrl = UPDATE, doc = "更新数据", req = UpdateRequest.class, resp = CountResult.class)
    CountResult update(UpdateRequest request) throws Exception;
    
}
