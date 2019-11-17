package com.x.commons.util.reflact;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @Date 2019-01-01 21:36
 * @Author AD
 */
public enum Fields {
    ;

    private static final Field[] EMPTY_FIELDS = new Field[0];

    public static Field[] getFields(Class<?> target) {
        return target.getDeclaredFields();
    }

    public static Field[] getFields(Class<?> target, Class<? extends Annotation> annotation) {
        final List<Field> fields =
                Stream.of(target.getDeclaredFields()).filter(f -> f.isAnnotationPresent(annotation)).collect(toList());
        return fields.isEmpty() ? EMPTY_FIELDS : fields.toArray(EMPTY_FIELDS);
    }

}

