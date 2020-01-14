package com.x.framework.caching.datas;

import java.lang.reflect.Method;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/14 16:36
 */
public final class ValueUpdater {

    private final String propName;

    private final Method method;

    private final Object value;

    public ValueUpdater(String propName, Method method, Object value) {
        this.propName = propName;
        this.method = method;
        this.value = value;
    }

    public String getPropName() {
        return propName;
    }

    /**
     * 将值通过反射机制设置到对象中
     *
     * @param o
     * @throws Exception
     */
    public void setValueTo(Object o) throws Exception {
        method.invoke(o, value);
    }


}
