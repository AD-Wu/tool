package com.x.protocol.annotations;

import com.x.protocol.core.IFormat;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Inherited
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface XField {
    String sid() default "";

    String doc();

    int min() default 0;

    int max() default 0;

    int length() default 0;

    String lengthProp() default "";

    Class<? extends IFormat> format() default IFormat.class;

    boolean pk() default false;
}
