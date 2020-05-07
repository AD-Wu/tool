package com.x.commons.util.reflact;

import com.x.commons.interfaces.IFilter;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @Date 2019-01-01 21:36
 * @Author AD
 */
public final class Fields {

    private static final Field[] EMPTY_FIELDS = new Field[0];

    private Fields() {}

    public static Field[] getFields(Class<?> target) {
        return target.getDeclaredFields();
    }

    public static Field[] getFields(Class<?> target, Class<? extends Annotation> annotation) {
        final List<Field> fields =
                Stream.of(target.getDeclaredFields()).filter(
                        f -> f.isAnnotationPresent(annotation)).collect(toList());
        return fields.isEmpty() ? EMPTY_FIELDS : fields.toArray(EMPTY_FIELDS);
    }

    public static Field[] getFields(Class<?> target, boolean parent) {
        return getFields(target, parent, null);
    }

    public static Field[] getFields(Class<?> target, boolean parent, IFilter<Field> filter) {
        if (target == null) {
            return EMPTY_FIELDS;
        }
        // 获取target类的public、protected、default、private的所有属性
        Field[] fields = target.getDeclaredFields();
        if (parent) {
            Field[] superFields = getFields(target.getSuperclass(), parent, filter);
            List<Field> list = New.list();
            if (filter != null) {
                for (Field superField : superFields) {
                    if (filter.accept(superField)) {
                        list.add(superField);
                    }
                }
                return XArrays.join(fields, list.toArray(new Field[0])).toArray(new Field[0]);
            } else {
                return XArrays.join(fields, superFields).toArray(new Field[0]);
            }
        }
        return fields;
    }
}

