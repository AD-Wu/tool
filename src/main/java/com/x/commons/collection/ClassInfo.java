package com.x.commons.collection;

import com.x.commons.util.string.Strings;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 14:14
 */
public final class ClassInfo {

    private final String name;

    private final Class<?> classType;

    public ClassInfo(String name, Class<?> classType) {
        this.name = name;
        this.classType = classType;
    }

    public String getName() {
        return name;
    }

    public Class<?> getClassType() {
        return classType;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
}
