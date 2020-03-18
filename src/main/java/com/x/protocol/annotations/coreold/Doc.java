package com.x.protocol.annotations.coreold;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @Date 2019-10-18 21:18
 * @Author AD
 */
@Inherited
@Documented
@Target(FIELD)
@Retention(SOURCE)
public @interface Doc {

    String value();
}
