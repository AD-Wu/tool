package com.x.framework.caching.datas;

import com.x.commons.collection.KeyValue;
import com.x.commons.util.string.Strings;
import com.x.framework.caching.datas.matchers.DateComparer;
import com.x.framework.caching.datas.matchers.IComparer;
import com.x.framework.caching.datas.matchers.LocalDateTimeComparer;
import com.x.framework.caching.datas.matchers.StringComparer;
import com.x.framework.caching.methods.MethodInfo;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;
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
                return desc.compare(method.invoke(o1), method.invoke(o2));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * @param getPropMap get方法信息，prop=属性名，methodInfo=对应的方法信息
     * @param kv         key=prop,value=asc|desc
     * @return
     */
    public static ValueSorter newInstance(Map<String, MethodInfo> getPropMap, KeyValue kv) {
        // 判断有效性
        if (kv == null || Strings.isNull(kv.getK())) {
            return null;
        }
        String prop = kv.getK().toUpperCase();
        MethodInfo methodInfo = getPropMap.get(prop);
        if (methodInfo == null) {
            return null;
        }
        // 判断是asc还是desc
        String v = Strings.toUppercase(kv.getV());
        // 默认asc（升序）
        boolean isAsc = Strings.isNull(v) || !"DESC".equals(v);

        // 获取返回值类型，判断出需要使用哪种类型的比较器
        Method method = methodInfo.getMethod();
        Class<?> type = method.getReturnType();
        IComparer asc = null;
        IComparer desc = null;
        if (!type.isPrimitive()) {
            if (type.equals(String.class)) {
                asc = isAsc ? StringComparer.LESS : StringComparer.GREATER;
                desc = isAsc ? StringComparer.GREATER : StringComparer.LESS;
            } else if (type.equals(Date.class)) {
                asc = isAsc ? DateComparer.LESS : DateComparer.GREATER;
                desc = isAsc ? DateComparer.GREATER : DateComparer.LESS;
            } else if (type.equals(LocalDateTime.class)) {
                asc = isAsc ? LocalDateTimeComparer.LESS : LocalDateTimeComparer.GREATER;
                desc = isAsc ? LocalDateTimeComparer.GREATER : LocalDateTimeComparer.LESS;
            }
        }

        return null;
    }
}
