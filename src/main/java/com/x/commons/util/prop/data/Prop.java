package com.x.commons.util.prop.data;

import com.x.commons.util.string.Strings;

/**
 * @Desc：xxx.properties文件里的键值对对象
 * @Author：AD
 * @Date：2020/3/23 11:52
 */
public final class Prop {
    // 注释
    private final String note;

    // 键
    private final String key;

    // 值
    private final String value;

    public Prop(String note, String key, String value) {
        this.note = note;
        this.key = key;
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
}
