package com.x.commons.decoder.core;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Date 2018-12-22 19:50
 * @Author AD
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Decoder {

    Format format();

}
