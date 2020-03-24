package com.x.framework.database.reader;

import com.x.commons.database.core.IDataReader;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.string.Strings;
import com.x.framework.caching.methods.MethodInfo;
import com.x.framework.database.core.Sqls;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Date 2019-12-13 21:28
 * @Author AD
 */
public class DaoPageReader<T> implements IDataReader {
    
    private final Class<T> dataClass;
    
    private final Map<String, MethodInfo> sets;
    
    private final int pageSize;
    
    private final List<T> datas;
    
    public DaoPageReader(Class<T> dataClass, Map<String, MethodInfo> sets, int pageSize) {
        this.dataClass = dataClass;
        this.sets = Collections.unmodifiableMap(sets);
        this.pageSize = Math.max(pageSize, 0);
        this.datas = New.list();
    }
    
    @Override
    public int read(ResultSet rs) throws Exception {
        // 设置查找行数
        rs.setFetchSize(pageSize);
        // 获取所有列
        ResultSetMetaData columns = rs.getMetaData();
        // 获取列数
        int count = columns.getColumnCount();
        int rows = 0;
        while (rs.next() && rows < pageSize) {
            ++rows;
            T data = Clazzs.newInstance(dataClass);
            for (int i = 1; i <= count; ++i) {
                String prop = columns.getColumnName(i);
                MethodInfo info = sets.get(Strings.toUppercase(prop));
                if (info != null) {
                    Object sqlValue = rs.getObject(prop);
                    Object param = Sqls.toJavaData(sqlValue, info);
                    Method method = info.getMethod();
                    method.invoke(data, param);
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
