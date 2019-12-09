package com.x.framework.caching.methods;

import com.x.commons.database.pool.DatabaseType;
import com.x.commons.util.bean.New;
import com.x.framework.database.core.Sqls;

import java.lang.reflect.Method;
import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * @Desc Bean类方法数据，一般是Get、Set
 * @Date 2019-12-05 22:13
 * @Author AD
 */
public final class MethodData {
    
    // ------------------------ 变量定义 ------------------------
    // bean类型
    private final Class<?> dataClass;
    
    // get方法信息数组
    private final MethodInfo[] methodsGet;
    
    // set方法信息数组
    private final MethodInfo[] methodsSet;
    
    // get方法信息数组map
    private final Map<String, MethodInfo> methodsGetMap = New.map();
    
    // set方法信息数组map
    private final Map<String, MethodInfo> methodsSetMap = New.map();
    
    // ------------------------ 构造方法 ------------------------
    
    /**
     * 构造方法
     *
     * @param dataClass 数据类型
     * @param dbType    数据库类型，可为null
     */
    public MethodData(Class<?> dataClass, DatabaseType dbType) {
        this.dataClass = dataClass;
        
        /**
         * 获取所有的方法进行遍历，将bean里面的方法封装成MethodInfo对象
         * 同时以大写属性名作为key存入map
         */
        Method[] ms = dataClass.getDeclaredMethods();
        List<MethodInfo> sets = New.list();
        List<MethodInfo> gets = New.list();
        
        for (int i = 0, L = ms.length; i < L; ++i) {
            Method method = ms[i];
            String name = method.getName();
            String NAME = name.toUpperCase();
            String key;
            String propName;
            int returnSqlType;
            if (NAME.startsWith("GET")) {
                // key是属性大写名，即映射表的字段名
                key = NAME.substring(3);
                // 属性名
                propName = name.substring(3);
                // 返回值所对应的数据库表sql类型
                returnSqlType = Sqls.getReturnSqlType(method);
                gets.add(new MethodInfo(key, propName, method, dbType, returnSqlType));
            } else if (NAME.startsWith("SET")) {
                // 获取参数类型
                int paramSqlType = Sqls.getParameterSqlType(method);
                if (paramSqlType != Types.NULL) {
                    // 字段名
                    key = NAME.substring(3);
                    // 属性名
                    propName = name.substring(3);
                    sets.add(new MethodInfo(key, propName, method, dbType, paramSqlType));
                }
            } else if (NAME.startsWith("IS")) {
                key = NAME.substring(2);
                propName = name.substring(2);
                returnSqlType = Sqls.getReturnSqlType(method);
                gets.add(new MethodInfo(key, propName, method, dbType, returnSqlType));
            }
        }
        
        this.methodsGet = new MethodInfo[gets.size()];
        
        MethodInfo methodInfo;
        for (int i = 0, L = gets.size(); i < L; ++i) {
            methodInfo = gets.get(i);
            this.methodsGet[i] = methodInfo;
            this.methodsGetMap.put(methodInfo.getKey(), methodInfo);
        }
        
        this.methodsSet = new MethodInfo[sets.size()];
        
        for (int i = 0, L = sets.size(); i < L; ++i) {
            methodInfo = sets.get(i);
            this.methodsSet[i] = methodInfo;
            this.methodsSetMap.put(methodInfo.getKey(), methodInfo);
        }
        
    }
    
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 获取 bean类型
     */
    public Class<?> getDataClass() {
        return this.dataClass;
    }
    
    /**
     * 获取 get方法信息数组
     */
    public MethodInfo[] getMethodsGet() {
        return this.methodsGet;
    }
    
    /**
     * 获取 set方法信息数组
     */
    public MethodInfo[] getMethodsSet() {
        return this.methodsSet;
    }
    
    /**
     * 获取 get方法信息数组map
     */
    public Map<String, MethodInfo> getMethodsGetMap() {
        return this.methodsGetMap;
    }
    
    /**
     * 获取 set方法信息数组map
     */
    public Map<String, MethodInfo> getMethodsSetMap() {
        return this.methodsSetMap;
    }
    
}
