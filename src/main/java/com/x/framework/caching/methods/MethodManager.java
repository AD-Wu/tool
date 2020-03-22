package com.x.framework.caching.methods;

import com.x.commons.database.pool.DatabaseType;
import com.x.commons.util.bean.New;

import java.util.Map;

/**
 * @Desc
 * @Date 2019-12-05 23:08
 * @Author AD
 */
public class MethodManager {
    
    private static final Map<Class<?>, MethodData> MAP = New.concurrentMap();
    
    private static final Object LOCK = new Object();
    
    private MethodManager() {}
    
    public static MethodData getMethodData(Class<?> beanClass) {
        MethodData data = MAP.get(beanClass);
        return data == null ? new MethodData(beanClass, null) : data;
    }
    
    public static MethodData getMethodData(Class<?> beanClass, DatabaseType dbType) {
        MethodData methodData = MAP.get(beanClass);
        if (methodData == null) {
            if (dbType == null) {
                return new MethodData(beanClass, dbType);
            }
            synchronized(LOCK) {
                methodData = MAP.get(beanClass);
                if (methodData == null) {
                    methodData = new MethodData(beanClass, dbType);
                    MAP.put(beanClass, methodData);
                }
            }
        }
        
        return methodData;
    }
}
