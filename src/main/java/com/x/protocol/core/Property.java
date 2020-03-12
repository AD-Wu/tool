package com.x.protocol.core;

import java.lang.reflect.Field;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/12 17:38
 */
public class Property {
    private String id;
    private String sid;
    private int min;
    private int max;
    private int length;
    private String lengthProp;
    private boolean pk;
    private String doc;
    private Field field;
    private Class<?> type;
    private Class<?> dataClass;
    private DataConfig dataConfig;

}
