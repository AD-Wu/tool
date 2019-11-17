package com.x.protocol.anno.core;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Date 2018-12-23 15:49
 * @Author AD
 */
@Inherited
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Recv {

    String ctrl();

    String doc();

    Class<?> req();

    Class<?> rep();

    boolean skipLogin() default false;

    boolean debugInfo() default true;

    short moduleID() default 0;

    short licenseID() default 0;


}
