package com.x.framework.database.core;

import com.ax.framework.database.core.TableInfo;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/19 15:44
 */
public interface ITableInfoGetter<T> {
    TableInfo getTableInfo(Class<T> var1);
}
