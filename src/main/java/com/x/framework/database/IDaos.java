package com.x.framework.database;

import com.x.commons.database.core.IDatabase;
import com.x.framework.database.core.ITableInfoGetter;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/19 15:46
 */
public interface IDaos {
    <T> IDao<T> getDao(Class<T> clazz);

    <T> IDao<T> getDao(Class<T> clazz, ITableInfoGetter<T> tableInfoGetter);

    IDatabase getDatabaseAccess();
}
