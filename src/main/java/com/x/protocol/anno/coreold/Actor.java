package com.x.protocol.anno.coreold;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Date 2018-12-23 15:49
 * @Author AD
 */
@Inherited
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Actor {

    String cmd();

    String doc();

    short systemID() default 0;
}
