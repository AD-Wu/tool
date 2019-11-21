package com.x.commons.util.string.annotation;

import com.x.commons.util.string.core.IStringParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Desc TODO
 * @Date 2019-11-21 23:05
 * @Author AD
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Parser {

    Class<?> parsed();

}
