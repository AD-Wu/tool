package com.x.framework.database;

import com.x.commons.collection.KeyValue;
import com.x.commons.collection.Where;
import com.x.commons.database.DatabaseAccess;
import com.x.commons.events.IListener;
import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.framework.database.core.SQLInfo;
import com.x.framework.database.core.SQLParams;
import com.x.framework.database.core.Sqls;
import com.x.framework.database.reader.DaoBeanReader;
import com.x.framework.database.reader.DaoCountReader;
import com.x.framework.database.reader.DaoListReader;
import com.x.framework.database.reader.DaoPageReader;
import com.x.protocol.core.IProtocol;

import java.lang.reflect.Array;
import java.util.List;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/19 15:59
 */
public class Dao<T> extends DatabaseAccess implements IDao<T> {
    private IProtocol protocol;

    private SQLInfo<T> sqlInfo;

    Dao(SQLInfo<T> sqlInfo) throws Exception {
        super("X");
        this.sqlInfo = sqlInfo;
    }

    Dao(IProtocol protocol, SQLInfo<T> sqlInfo) throws Exception {
        super(protocol.getName());
        this.protocol = protocol;
        this.sqlInfo = sqlInfo;
    }

    @Override
    public String[] getPrimaryKeys() {
        return this.sqlInfo.getPrimaryKeys();
    }

    @Override
    public void refreshCache() {
        throw new IllegalArgumentException("Not caching data class: " + this.getClass().getName());
    }

    @Override
    public void addCacheListener(String type, IListener listener) {
        throw new IllegalArgumentException("Not caching data class: " + this.getClass().getName());
    }

    @Override
    public void addCacheListener(String type, IListener listener, int action) {
        throw new IllegalArgumentException("Not caching data class: " + this.getClass().getName());
    }

    @Override
    public boolean hasCacheListener(String type) {
        throw new IllegalArgumentException("Not caching data class: " + this.getClass().getName());
    }

    @Override
    public boolean hasCacheListener(String type, IListener listener) {
        throw new IllegalArgumentException("Not caching data class: " + this.getClass().getName());
    }

    @Override
    public void removeCacheListener(String type) {
        throw new IllegalArgumentException("Not caching data class: " + this.getClass().getName());
    }

    @Override
    public void removeAllCacheListeners() {
        throw new IllegalArgumentException("Not caching data class: " + this.getClass().getName());
    }

    @Override
    public void removeCacheListener(String type, IListener listener) {
        throw new IllegalArgumentException("Not caching data class: " + this.getClass().getName());
    }

    @Override
    public T add(T bean) throws Exception {
        SQLParams sqlParams = this.sqlInfo.getCreate(bean);
        if (sqlParams == null) {
            return null;
        } else {
            try {
                return this.execute(sqlParams.getSql(), sqlParams.getParams(),
                                    sqlParams.getTypes()) > 0 ? bean : null;
            } catch (Exception e) {
                // this.protocol.getLogger().error(
                //         Locals.text("framework.db.add.err", this.sqlInfo.getDataClass()));
                throw e;
            }
        }
    }

    @Override
    public T[] addAll(T[] beans) throws Exception {
        if (beans == null) {
            return (T[]) Array.newInstance(sqlInfo.getDataClass(), 0);
        } else {
            List<T> dataList = New.list();

            for (int i = 0, L = beans.length; i < L; ++i) {
                T data = beans[i];
                SQLParams sqlParams = this.sqlInfo.getCreate(data);
                if (sqlParams != null) {
                    try {
                        if (super.execute(sqlParams.getSql(), sqlParams.getParams(),
                                          sqlParams.getTypes()) > 0) {
                            dataList.add(data);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            T[] result = (T[]) Array.newInstance(sqlInfo.getDataClass(), dataList.size());
            dataList.toArray(result);
            return result;
        }
    }

    @Override
    public T put(T bean) throws Exception {
        SQLParams sqlParams = sqlInfo.getCountByPrimary(bean);
        if (sqlParams == null) {
            return null;
        } else {
            DaoCountReader reader = new DaoCountReader();
            this.executeReader(reader, sqlParams.getSql(), sqlParams.getParams(),
                               sqlParams.getTypes());
            if (reader.getCount() > 0) {
                return this.edit(bean) > 0 ? bean : null;
            } else {
                return this.add(bean);
            }
        }
    }

    @Override
    public T[] putAll(T[] beans) throws Exception {
        if (beans == null) {
            return (T[]) Array.newInstance(this.sqlInfo.getDataClass(), 0);
        } else {
            List<T> dataList = New.list();

            for (int i = 0, L = beans.length; i < L; ++i) {
                T data = beans[i];

                try {
                    T t = this.put(data);
                    if (t != null) {
                        dataList.add(t);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            T[] result = (T[]) Array.newInstance(sqlInfo.getDataClass(), dataList.size());
            dataList.toArray(result);
            return result;
        }
    }

    @Override
    public int delete(Where[] wheres) throws Exception {
        SQLParams sqlParams = sqlInfo.getDelete(wheres);
        if (sqlParams == null) {
            return -1;
        } else {
            try {
                return super.execute(sqlParams.getSql(), sqlParams.getParams(),
                                     sqlParams.getTypes());
            } catch (Exception e) {
                this.protocol.getLogger().error(
                        Locals.text("framework.db.delete.err", this.sqlInfo.getDataClass()));
                throw e;
            }
        }
    }

    @Override
    public int edit(T bean) throws Exception {
        SQLParams sqlParams = sqlInfo.getUpdateBean(bean);
        if (sqlParams == null) {
            return -1;
        } else {
            try {
                return this.execute(sqlParams.getSql(), sqlParams.getParams(),
                                    sqlParams.getTypes());
            } catch (Exception e) {
                this.protocol.getLogger().error(
                        Locals.text("framework.db.edit.err", this.sqlInfo.getDataClass()));
                throw e;
            }
        }
    }

    @Override
    public T getBean(Where[] wheres) throws Exception {
        SQLParams sqlParams = this.sqlInfo.getRetrieve(wheres);
        if (sqlParams == null) {
            return null;
        } else {
            try {
                DaoBeanReader<T> reader = sqlInfo.getBeanReader();
                return super.executeReader(reader, sqlParams.getSql(), sqlParams.getParams(),sqlParams.getTypes()) > 0 ? reader.getData() : null;
            } catch (Exception e) {
                protocol.getLogger().error(
                        Locals.text("framework.db.bean.err", sqlInfo.getDataClass()));
                throw e;
            }
        }
    }

    @Override
    public T getByPrimary(Object... pks) throws Exception {
        SQLParams sqlParams = sqlInfo.getByPrimary(pks);
        if (sqlParams == null) {
            return null;
        } else {
            try {
                DaoBeanReader<T> reader = sqlInfo.getBeanReader();
                return super.executeReader(reader, sqlParams.getSql(), sqlParams.getParams(),
                                           sqlParams.getTypes()) > 0 ? reader.getData() : null;
            } catch (Exception e) {
                protocol.getLogger().error(
                        Locals.text("framework.db.bean.err", this.sqlInfo.getDataClass()));
                throw e;
            }
        }
    }

    @Override
    public boolean contains(Where[] wheres) throws Exception {
        return this.getCount(wheres) > 0;
    }

    @Override
    public int getCount(Where[] wheres) throws Exception {
        SQLParams sqlParams = sqlInfo.getCount(wheres);
        if (sqlParams == null) {
            return -1;
        } else {
            try {
                DaoCountReader reader = new DaoCountReader();
                super.executeReader(reader, sqlParams.getSql(), sqlParams.getParams(),
                                    sqlParams.getTypes());
                return reader.getCount();
            } catch (Exception e) {
                protocol.getLogger().error(
                        Locals.text("framework.db.count.err", sqlInfo.getDataClass()));
                throw e;
            }
        }
    }

    @Override
    public T[] getList(Where[] wheres, KeyValue[] orders) throws Exception {
        SQLParams sqlParams = sqlInfo.getRetrieve(wheres, orders);
        if (sqlParams == null) {
            return null;
        } else {
            try {
                DaoListReader<T> reader = sqlInfo.getListReader();
                super.executeReader(reader, sqlParams.getSql(), sqlParams.getParams(),
                                    sqlParams.getTypes());
                return reader.getDatas();
            } catch (Exception e) {
                protocol.getLogger().error(
                        Locals.text("framework.db.list.err", this.sqlInfo.getDataClass()));
                throw e;
            }
        }
    }

    @Override
    public T[] getPage(int page, int pageSize, Where[] wheres, KeyValue[] orders) throws Exception {
        SQLParams sqlParams = sqlInfo.getRetrieve(wheres, orders);
        if (sqlParams == null) {
            return null;
        } else {
            if (page <= 0) {
                page = 1;
            }

            if (pageSize <= 0) {
                pageSize = 1;
            }

            int totalLength = (page - 1) * pageSize;

            try {
                DaoPageReader<T> reader = sqlInfo.getPageReader(pageSize);
                super.executeReader(reader, sqlParams.getSql(), sqlParams.getParams(),
                                    sqlParams.getTypes(), totalLength,
                                    pageSize);
                return reader.getDatas();
            } catch (Exception e) {
                protocol.getLogger().error(
                        Locals.text("framework.db.page.err", this.sqlInfo.getDataClass()));
                throw e;
            }
        }
    }

    @Override
    public int update(KeyValue[] updates, Where[] wheres) throws Exception {
        SQLParams sqlParams = sqlInfo.getUpdate(updates, wheres);
        if (sqlParams == null) {
            return -1;
        } else {
            try {
                return super.execute(sqlParams.getSql(), sqlParams.getParams(),
                                     sqlParams.getTypes());
            } catch (Exception e) {
                protocol.getLogger().error(
                        Locals.text("framework.db.update.err", this.sqlInfo.getDataClass()));
                throw e;
            }
        }
    }

    @Override
    public T add(String[] columns, Object[] values) throws Exception {
        T data = sqlInfo.createBean(columns, values);
        return data == null ? null : this.add(data);
    }

    @Override
    public int delete(String[] columns, Object[] values) throws Exception {
        return this.delete(Sqls.getWheres(columns, values));
    }

    @Override
    public T getBean(String[] columns, Object[] values) throws Exception {
        return this.getBean(Sqls.getWheres(columns, values));
    }

    @Override
    public boolean contains(String[] columns, Object[] values) throws Exception {
        return this.getCount(Sqls.getWheres(columns, values)) > 0;
    }

    @Override
    public int getCount(String[] columns, Object[] values) throws Exception {
        return this.getCount(Sqls.getWheres(columns, values));
    }

    @Override
    public T[] getList(String[] columns, Object[] values, KeyValue[] orders) throws Exception {
        return this.getList(Sqls.getWheres(columns, values), orders);
    }

    @Override
    public T[] getPage(int page, int pageSize, String[] columns, Object[] values, KeyValue[] orders) throws Exception {
        return this.getPage(page, pageSize, Sqls.getWheres(columns, values), orders);
    }

    @Override
    public int update(String[] updateColumns, Object[] updateValues, String[] whereColumns, Object[] whereValues) throws Exception {
        return this.update(Sqls.getUpdates(updateColumns, updateValues), Sqls.getWheres(whereColumns, whereValues));
    }
}
