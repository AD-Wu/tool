package com.x.framework.caching.datas;

import com.x.commons.collection.KeyValue;
import com.x.commons.util.string.Strings;
import com.x.framework.caching.datas.matchers.IComparer;
import com.x.framework.caching.methods.MethodInfo;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/14 17:14
 */
public final class ValueSorter {
    private final Method method;

    private final IComparer asc;

    private final IComparer desc;

    public ValueSorter(Method method, IComparer asc, IComparer desc) {
        this.method = method;
        this.asc = asc;
        this.desc = desc;
    }

    public boolean MatchAsc(Object o1, Object o2) {
        if (o1 == o2) {
            return false;
        } else if (o1 == null && o2 != null) {
            return true;
        } else if (o1 != null && o2 == null) {
            return false;
        } else {
            try {
                return asc.compare(method.invoke(o1), method.invoke(o2));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean MatchDesc(Object o1, Object o2) {
        if (o1 == o2) {
            return false;
        } else if (o1 == null && o2 != null) {
            return false;
        } else if (o1 != null && o2 == null) {
            return true;
        } else {
            try {
                return asc.compare(method.invoke(o1), method.invoke(o2));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * @param propMap prop=属性名，methodInfo=对应的方法信息
     * @param kv      key=prop,value=asc|desc
     * @return
     */
    public static ValueSorter newInstance(Map<String, MethodInfo> propMap, KeyValue kv) {
        if (kv == null || Strings.isNull(kv.getK())) {
            return null;
        }
        String prop = kv.getK();
        MethodInfo methodInfo = propMap.get(prop);
        Method method = methodInfo.getMethod();

        return null;
    }
}
