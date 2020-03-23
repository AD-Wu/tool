package com.x.commons.util.prop.data;

import com.x.commons.util.bean.New;
import com.x.commons.util.string.Strings;

import java.util.Map;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/23 11:50
 */
public final class Part {
    private final String note;

    private final String name;

    private final Map<String, Prop> props;

    public Part(String note, String name) {
        this.note = note;
        this.name = name;
        this.props = New.linkedMap();
    }

    public String getNote() {
        return note;
    }

    public String getName() {
        return name;
    }

    public Map<String, Prop> getPropMap() {
        return props;
    }

    public Prop[] getProps() {
        return props.values().toArray(new Prop[0]);
    }

    public void putProp(Prop prop) {
        if (prop != null && Strings.isNotNull(prop.getKey())) {
            props.put(prop.getKey(), prop);
        }
    }

    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
}
