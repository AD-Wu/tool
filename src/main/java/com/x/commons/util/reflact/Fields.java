package com.x.commons.util.reflact;

import com.x.commons.util.collection.XArrays;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
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
        if (target == null) {
            return EMPTY_FIELDS;
        }
        // 获取target类的public、protected、default、private的所有属性
        Field[] fields = target.getDeclaredFields();
        if (parent) {
            Field[] superFields = getFields(target.getSuperclass(), parent);
            return XArrays.join(fields, superFields).toArray(new Field[0]);
        }
        return fields;
    }

    public static void main(String[] args) {
        Field[] fields = getFields(Subb.class, true);
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }

    public static class Parent {
        public String name;

        protected int age;

        boolean sex;

        private LocalDateTime birthday;
    }

    public static class Sub extends Parent {
        public String addr;

        protected int number;

        int count;

        private boolean rich;
    }
    public static class Subb extends Sub{
        private String test;
    }
}

