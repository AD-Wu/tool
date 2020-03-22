package com.x.framework.protocol.actor;

import com.x.commons.local.Locals;
import com.x.framework.database.IDao;
import com.x.framework.database.core.ITableInfoGetter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Desc
 * @Date 2020-03-22 22:25
 * @Author AD
 */
public abstract class DaoActor<T> extends BaseActor {
    protected final IDao<T> dao;
    
    protected DaoActor(String name, boolean needResponse, ITableInfoGetter<T> getter) {
        super(name, needResponse);
        Type superClass = this.getClass().getGenericSuperclass();
        if(superClass instanceof Class){
            throw new IllegalArgumentException(Locals.text("framework.actor.type", this.getClass()));
        }else{
            Type[] types = ((ParameterizedType) superClass).getActualTypeArguments();
            Class<T> dataClass = (Class) types[0];
            this.dao = daoManager.getDao(dataClass, getter);
        }
    }
    
}
