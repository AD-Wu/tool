package com.x.protocol.anno.info;

import com.x.commons.util.bean.New;
import com.x.commons.util.reflact.Fields;
import com.x.protocol.anno.core.XData;
import com.x.protocol.anno.core.XField;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @Date 2018-12-30 23:00
 * @Author AD
 */
@Data
public final class DataInfo {

    private String doc;
    private boolean cache;
    private boolean history;
    private String table;
    private String version;

    private Class<?> selfClass;
    private FieldInfo[] fieldInfos;
    private final Map<String, FieldInfo> map = New.map();

    public DataInfo(@NonNull Class<?> data) {
        if (data.isAnnotationPresent(XData.class)) {
            final XData x = data.getAnnotation(XData.class);
            this.doc = x.doc();
            this.cache = x.cache();
            this.history = x.history();
            this.table = x.table();
            this.version = x.version();
            this.selfClass = data;
            init();

        }
    }

    @SneakyThrows
    public Object getFieldValue(String lengthProp, Object data) {
        return map.get(lengthProp).getField().get(data);
    }

    private void init() {

        final Field[] fs = Fields.getFields(selfClass, XField.class);
        final FieldInfo[] infos = new FieldInfo[fs.length];
        for (int i = 0, c = infos.length; i < c; ++i) {
            final Field f = fs[i];
            final FieldInfo info = new FieldInfo(f);
            infos[i] = info;
            map.put(f.getName(), info);
        }
        fieldInfos = infos;
    }

}
