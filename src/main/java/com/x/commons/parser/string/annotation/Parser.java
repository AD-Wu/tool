package com.x.commons.parser.string.annotation;

import java.lang.annotation.*;

/**
 * @Desc TODO
 * @Date 2019-11-21 23:05
 * @Author AD
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Parser {

    Class<?>[] result();

}
