package com.x.framework.database.core;

import com.x.commons.collection.KeyValue;
import com.x.commons.collection.Where;
import com.x.commons.database.pool.DatabaseType;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.string.Strings;
import com.x.framework.caching.methods.MethodData;
import com.x.framework.caching.methods.MethodInfo;
import com.x.framework.caching.methods.MethodManager;
import com.x.framework.database.reader.DaoBeanReader;
import com.x.framework.database.reader.DaoListReader;
import com.x.framework.database.reader.DaoPageReader;

import java.util.List;
import java.util.Map;

/**
 * @Desc SQL信息
 * @Date 2019-12-05 22:01
 * @Author AD
 */
public class SQLInfo<T> {
    
    // 创建语句
    private String createSQL;
    
    // 删除语句
    private String deleteSQL;
    
    // 查找语句
    private String retrieveSQL;
    
    // 主键
    private String retrievePK;
    
    // 查询条数语句
    private String countSQL;
    
    // 更新语句
    private String updateSQL;
    
    // 更新Bean对象语句
    private String updateBeanSQL;
    
    // 数据库类型
    private DatabaseType dbType;
    
    // 所对应的java类
    private Class<T> dataClass;
    
    // 缓存
    private boolean caching;
    
    // 缓存历史
    private boolean cachingHistory;
    
    // 主键数组
    private String[] primaryKeys = XArrays.EMPTY_STRING;
    
    // 大写PK
    private List<String> PKList = New.list();
    
    // 类里面的所有方法数据
    private MethodData methodData;
    
    /**
     * 构造方法
     *
     * @param dataClass Bean类
     * @param tableInfo 带注解的表信息，如@XData
     * @param dbType
     */
    public SQLInfo(Class<T> dataClass, TableInfo tableInfo, DatabaseType dbType) {
        
        String tableName;
        // 没有用注解
        if (tableInfo == null) {
            tableName = dataClass.getSimpleName();
            this.caching = false;
            this.cachingHistory = false;
        } else {
            // 用注解
            tableName = tableInfo.getTableName();
            if (Strings.isNull(tableName)) {
                tableName = dataClass.getSimpleName();
            }
            this.caching = tableInfo.isCaching();
            this.cachingHistory = tableInfo.isCachingHistory();
        }
        
        // 根据数据库类型修正表名
        if (dbType != DatabaseType.MYSQL) {
            if (dbType == DatabaseType.SQLSERVER) {
                tableName = "[" + tableName + "]";
            } else {
                tableName = "\"" + tableName + "\"";
            }
        }
        // 表名转换为大写
        tableName = tableName.toUpperCase();
        // 数据库类型
        this.dbType = dbType;
        // bean数据类型
        this.dataClass = dataClass;
        // bean里的方法信息
        this.methodData = MethodManager.getMethodData(dataClass, dbType);
        // 删除语句
        this.deleteSQL = "DELETE FROM " + tableName;
        // 查找语句
        this.retrieveSQL = "SELECT * FROM " + tableName;
        // 数目语句
        this.countSQL = "SELECT COUNT(*) C FROM " + tableName;
        // 更新语句
        this.updateSQL = "UPDATE " + tableName + " SET ";
        // 所有的Get方法信息
        MethodInfo[] gets = this.methodData.getMethodsGet();
        // 属性容器，即表字段容器
        List<String> PROPs = New.list();
        // 初始化属性容器，用于以下主键和非主键语句的生成
        for (MethodInfo get : gets) {
            // 方法信息的key，即属性，在封装时都转为大写了
            PROPs.add(get.getKey());
        }
        // 生成插入语句
        this.createSQL = "INSERT INTO " + tableName + " (" + Converts.toString(PROPs) + ") VALUES (" +
                         Strings.duplicate("?", ",", PROPs.size()) + ")";
        // 根据有注解的类，生成根据主键更新和查找的语句
        SB sb = New.sb();
        if (tableInfo != null && tableInfo.getPrimaryKeys() != null) {
            String[] pks = tableInfo.getPrimaryKeys();
            for (int i = 0, L = pks.length; i < L; ++i) {
                String pk = pks[i];
                if (!Strings.isNull(pk)) {
                    String PK = pk.toUpperCase();
                    PKList.add(PK);
                    // 在属性字段里，将PK的字段移除
                    PROPs.remove(PK);
                    if (sb.length() == 0) {
                        sb.append(PK);
                        sb.append("=?");
                    } else {
                        sb.append(" AND ");
                        sb.append(PK);
                        sb.append("=?");
                    }
                }
            }
        }
        // 有主键
        if (this.PKList.size() > 0) {
            this.primaryKeys = PKList.toArray(XArrays.EMPTY_STRING);
            String pkSQL = sb.toString();
            // 根据主键查找的语句
            this.retrievePK = "SELECT * FROM " + tableName + " WHERE " + pkSQL;
            if (PROPs.size() > 0) {
                // 根据主键更新的语句
                this.updateBeanSQL =
                        "UPDATE " + tableName + " SET " + Converts.toString(PROPs, "=?,") + "=? WHERE " + pkSQL;
            }
        } else {
            // 没有主键的，可能是没有使用注解的，并不是关系映射表
            this.caching = false;
            this.cachingHistory = false;
        }
        
    }
    
    /**
     * 根据给定的字段名和值创建Bean对象
     *
     * @param columns 表字段，即Bean对象属性
     * @param values  字段值
     *
     * @return
     *
     * @throws Exception
     */
    public T createBean(String[] columns, Object[] values) throws Exception {
        if (columns != null && values != null && columns.length == values.length) {
            T t = Clazzs.newInstance(dataClass);
            Map<String, MethodInfo> setMap = methodData.getMethodsSetMap();
            
            for (int i = 0, L = columns.length; i < L; ++i) {
                String column = columns[i];
                if (Strings.isNull(column)) {
                    return null;
                }
                MethodInfo info = setMap.get(column.toUpperCase());
                if (info == null) {
                    return null;
                }
                info.getMethod().invoke(t, values[i]);
            }
            return t;
        } else {
            return null;
        }
    }
    
    public SQLParams getCreate(T t) {
        SQLParams params = Sqls.getCreateParams(methodData.getMethodsGet(), methodData.getMethodsSetMap(), PKList, t);
        return params == null ? null : new SQLParams(this.createSQL, params.getParams(), params.getTypes());
    }
    
    public SQLParams getByPrimary(Object[] pks) {
        if (retrievePK != null && pks != null && pks.length == PKList.size()) {
            SQLParams sql = Sqls.getPrimaryParams(methodData.getMethodsGetMap(), PKList, pks);
            return sql == null ? null : new SQLParams(retrievePK, sql.getParams(), sql.getTypes());
        } else {
            return null;
        }
    }
    
    public SQLParams getRetrieve(Where[] wheres) {
        SQLParams sql = getWhere(wheres);
        return sql == null ? null : new SQLParams(retrieveSQL + sql.getSql(), sql.getParams(), sql.getTypes());
    }
    
    public SQLParams getRetrieve(Where[] wheres, KeyValue[] kvs) {
        SQLParams whereInfo = getWhere(wheres);
        if (whereInfo == null) {
            return null;
        } else {
            String orderSql = getOrderBy(kvs);
            if (Strings.isNull(orderSql)) {
                return null;
            } else {
                String whereSQL = whereInfo.getSql();
                return new SQLParams(this.retrieveSQL + whereSQL + orderSql, whereInfo.getParams(), whereInfo.getTypes());
            }
        }
    }
    
    public SQLParams getCount(Where[] wheres) {
        SQLParams whereParam = this.getWhere(wheres);
        return whereParam == null ? null : new SQLParams(
                this.countSQL + whereParam.getSql(), whereParam.getParams(), whereParam.getTypes());
    }
    
    public SQLParams getCountByPrimary(T data) throws Exception {
        SQLParams pkParam = Sqls.getPrimaryParamsByBean(methodData.getMethodsGetMap(), PKList, data);
        return pkParam == null ? null : new SQLParams(this.countSQL + pkParam.getSql(), pkParam.getParams(), pkParam.getTypes());
    }
    
    public SQLParams getUpdate(KeyValue[] kvs, Where[] wheres) {
        SQLParams updateParam = Sqls.getUpdateParams(methodData.getMethodsGetMap(), kvs);
        if (updateParam == null) {
            return null;
        } else {
            String updateSql = updateParam.getSql();
            if (Strings.isNull(updateSql)) {
                return null;
            } else {
                SQLParams whereParam = this.getWhere(wheres);
                if (whereParam == null) {
                    return null;
                } else {
                    String whereSQL = whereParam.getSql();
                    Object[] params = Converts.concat(updateParam.getParams(), whereParam.getParams());
                    int[] sqlTypes = Converts.concat(updateParam.getTypes(), whereParam.getTypes());
                    return new SQLParams(this.updateSQL + updateSql + whereSQL, params, sqlTypes);
                }
            }
        }
    }
    
    public SQLParams getUpdateBean(T data) {
        if (Strings.isNull(updateBeanSQL)) {
            return null;
        } else {
            SQLParams sql = Sqls.getUpdateBeanParams(methodData.getMethodsGet(), PKList, data);
            return sql == null ? null : new SQLParams(this.updateBeanSQL, sql.getParams(), sql.getTypes());
        }
    }
    
    public SQLParams getDelete(Where[] wheres) {
        SQLParams whereParam = this.getWhere(wheres);
        if (whereParam == null) {
            return null;
        } else {
            String whereSQL = whereParam.getSql();
            return new SQLParams(this.deleteSQL + whereSQL, whereParam.getParams(), whereParam.getTypes());
        }
    }
    
    public DatabaseType getType() {
        return this.dbType;
    }
    
    public Class<T> getDataClass() {
        return this.dataClass;
    }
    
    public boolean isCaching() {
        return this.caching;
    }
    
    public boolean isCachingHistory() {
        return this.cachingHistory;
    }
    
    public String[] getPrimaryKeys() {
        return this.primaryKeys;
    }
    
    public SQLParams getWhere(Where[] wheres) {
        return Sqls.getWhereParams(methodData.getMethodsGetMap(), wheres);
    }
    
    public String getOrderBy(KeyValue[] kvs) {
        return Sqls.getOrderBy(methodData.getMethodsGetMap(), kvs);
    }
    
    public DaoBeanReader<T> getBeanReader() {
        return new DaoBeanReader(this.dataClass, methodData.getMethodsSetMap());
    }
    
    public DaoListReader<T> getListReader() {
        return new DaoListReader(this.dataClass, methodData.getMethodsSetMap());
    }
    
    public DaoPageReader<T> getPageReader(int pageSize) {
        return new DaoPageReader(this.dataClass, methodData.getMethodsSetMap(), pageSize);
    }
    
}
