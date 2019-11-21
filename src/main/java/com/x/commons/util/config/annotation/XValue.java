package com.x.commons.util.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author AD
 * @Date 2019/11/21 9:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XValue {

    String key();

    Class<?> formatClass() default Void.class;

    String methodName() default "";

}
