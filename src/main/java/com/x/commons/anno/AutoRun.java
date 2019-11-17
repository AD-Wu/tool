package com.x.commons.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author AD
 * @Date 2018/12/21 14:31
 */

@Target(METHOD)
@Retention(RUNTIME)
public @interface AutoRun {
}
