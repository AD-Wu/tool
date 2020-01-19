package com.x.framework.database;

import com.ax.commons.database.core.IDatabase;
import com.ax.framework.database.IDao;
import com.ax.framework.database.core.ITableInfoGetter;

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
