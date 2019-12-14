package com.x.framework.database.reader;

import com.x.commons.database.reader.IDataReader;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.string.Strings;
import com.x.framework.caching.methods.MethodInfo;
import com.x.framework.database.core.Sqls;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Date 2019-12-13 20:24
 * @Author AD
 */
public class DaoListReader<T> implements IDataReader {
    
    private final Class<T> dataClass;
    
    private final Map<String, MethodInfo> setMethodMap;
    
    private List<T> datas;
    
    public DaoListReader(Class<T> dataClass, Map<String, MethodInfo> setMethodMap) {
        this.dataClass = dataClass;
        this.setMethodMap = setMethodMap;
        this.datas = New.list();
    }
    
    @Override
    public int read(ResultSet rs) throws Exception {
        ResultSetMetaData columns = rs.getMetaData();
        int count = columns.getColumnCount();
        int rows = 0;
        while (rs.next()) {
            ++rows;
            T data = Clazzs.newInstance(dataClass);
            for (int i = 1; i <= count; ++i) {
                String prop = columns.getColumnName(i);
                MethodInfo info = setMethodMap.get(Strings.toUppercase(prop));
                if (info != null) {
                    Object sqlValue = rs.getObject(prop);
                    Object param = Sqls.toJavaData(sqlValue, info);
                    info.getMethod().invoke(data, param);
                    datas.add(data);
                }
            }
        }
        
        return rows;
    }
    
    public T[] getDatas() {
        return XArrays.toArray(datas, dataClass);
    }
    
}
