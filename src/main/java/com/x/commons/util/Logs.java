package com.x.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc TODO
 * @Date 2019-11-01 22:38
 * @Author AD
 */
public class Logs {

    public static Logger of(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

}
