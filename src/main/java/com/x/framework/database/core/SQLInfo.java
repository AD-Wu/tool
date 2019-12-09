package com.x.framework.database.core;

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
    
    private List<String> upperPKList = New.list();
    
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
        List<String> props = New.list();
        // 初始化属性容器，用于以下主键和非主键语句的生成
        for (int i = 0, L = gets.length; i < L; ++i) {
            MethodInfo info = gets[i];
            props.add(info.getKey());
        }
        // 生成插入语句
        this.createSQL = "INSERT INTO " + tableName + " (" + Converts.toString(props) + ") VALUES (" +
                         Strings.duplicate("?", ",", props.size()) + ")";
        // 根据有注解的类，生成根据主键更新和查找的语句
        SB sb = New.sb();
        if (tableInfo != null && tableInfo.getPrimaryKeys() != null) {
            String[] pks = tableInfo.getPrimaryKeys();
            for (int i = 0, L = pks.length; i < L; ++i) {
                String pk = pks[i];
                if (!Strings.isNull(pk)) {
                    String PK = pk.toUpperCase();
                    upperPKList.add(PK);
                    // 在属性字段里，将PK的字段移除
                    props.remove(PK);
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
        if (this.upperPKList.size() > 0) {
            this.primaryKeys = upperPKList.toArray(new String[upperPKList.size()]);
            String pkSQL = sb.toString();
            // 根据主键查找的语句
            this.retrievePK = "SELECT * FROM " + tableName + " WHERE " + pkSQL;
            if (props.size() > 0) {
                // 根据主键更新的语句
                this.updateBeanSQL =
                        "UPDATE " + tableName + " SET " + Converts.toString(props, "=?,") + "=? WHERE " + pkSQL;
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
        SQLParams params = Sqls.getCreateParams(methodData.getMethodsGet(), methodData.getMethodsSetMap(), upperPKList, t);
        return params == null ? null : new SQLParams(this.createSQL, params.getParams(), params.getTypes());
    }
}
