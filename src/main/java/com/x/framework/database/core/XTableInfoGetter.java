package com.x.framework.database.core;

import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.protocol.annotations.XData;
import com.x.protocol.annotations.XField;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @Desc
 * @Date 2020-03-22 23:51
 * @Author AD
 */
public class XTableInfoGetter<T> implements ITableInfoGetter<T> {
    
    @Override
    public TableInfo getTableInfo(Class<T> clazz) {
        if (clazz.isAnnotationPresent(XData.class)) {
            XData xData = clazz.getAnnotation(XData.class);
            String table = xData.table();
            boolean cache = xData.cache();
            boolean history = xData.history();
            Field[] fields = clazz.getDeclaredFields();
            List<String> pks = New.list();
            Arrays.stream(fields).forEach(field -> {
                if (field.isAnnotationPresent(XField.class)) {
                    XField xf = field.getAnnotation(XField.class);
                    if (xf.pk()) {
                        String pk = field.getName();
                        pks.add(pk);
                    }
                }
            });
            new TableInfo(table, pks.toArray(XArrays.EMPTY_STRING), cache, history);
        }
        return null;
    }
    
}
