package com.x.commons.collection;

import com.x.commons.util.string.Strings;
import com.x.protocol.annotations.XData;
import com.x.protocol.annotations.XField;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2019-11-08 12:08
 * @Author AD
 */
@XData(cache = false, doc = "名称值数据")
public final class NameValue implements Serializable {
    private static final long serialVersionUID = 1L;

    // ---------------------- 成员变量 ----------------------

    @XField(doc = "数据名称")
    private String name;

    @XField(doc = "数据值")
    private String value;

    // ---------------------- 构造方法 ----------------------

    public NameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // ---------------------- 成员方法 ----------------------

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }

}
