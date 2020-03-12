package com.x.commons.collection;

import com.x.commons.util.string.Strings;
import com.x.protocol.anno.coreold.Doc;

import java.io.Serializable;

/**
 * @Desc TODO
 * @Date 2019-11-08 12:08
 * @Author AD
 */
public final class NameValue implements Serializable {

    // ---------------------- 成员变量 ----------------------

    @Doc("数据名称")
    private final String name;

    @Doc("数据值")
    private final String value;

    // ---------------------- 构造方法 ----------------------

    public NameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // ---------------------- 成员方法 ----------------------

    /**
     * 获取 数据名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 获取 数据值
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }

}
