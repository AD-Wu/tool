package com.x.protocol.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Inherited
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface XSend {

    String ctrl();

    String doc();

    Class<?> req();

    Class<?> resp();

    boolean debug() default true;

    short moduleID() default 0;

    short licenseID() default 0;
}
