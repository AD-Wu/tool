package com.x.framework.protocol.actor;

import com.x.framework.protocol.actor.model.CountResult;
import com.x.framework.protocol.actor.model.PageRequest;
import com.x.framework.protocol.actor.model.WhereRequest;
import com.x.protocol.annotations.XAuto;
import com.x.protocol.annotations.XRecv;

import java.io.Serializable;

import static com.x.framework.protocol.actor.QueryActor.*;

/**
 * @Desc
 * @Date 2020-03-21 22:12
 * @Author AD
 */
public interface IQueryActor<T extends Serializable> {
    
    @XRecv(ctrl = GET, doc = "获取数据", req = WhereRequest.class, resp = XAuto.class)
    T getBean(WhereRequest request) throws Exception;
    
    @XRecv(ctrl = COUNT, doc = "获取数据总数", req = WhereRequest.class, resp = CountResult.class)
    CountResult getCount(WhereRequest request) throws Exception;
    
    @XRecv(ctrl = LIST, doc = "获取数据列表", req = WhereRequest.class, resp = XAuto.class)
    T[] getList(WhereRequest request) throws Exception;
    
    @XRecv(ctrl = PAGE, doc = "获取分页数据", req = WhereRequest.class, resp = XAuto.class)
    T[] getPage(PageRequest request) throws Exception;
    
}
