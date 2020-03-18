package com.x.protocol.annotations.coreold;

import com.x.commons.decoder.enums.Format;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Date 2018-12-23 15:47
 * @Author AD
 */
@Inherited
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface XField {

    String sid() default "";

    String doc();

    int length() default 0;

    int skipByte() default 0;

    int min() default 0;

    int max() default 0;

    String lengthProp() default "";

    Format format() default Format.NULL;

    boolean pk() default false;

}
