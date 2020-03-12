package com.x.protocol.core;

import com.x.commons.util.bean.New;

import java.util.Map;

/**
 * @Desc
 * @Date 2020-01-19 21:54
 * @Author AD
 */
public class DataConfig {
    /**
     * bean说明
     */
    private String doc;

    /**
     * 是否缓存
     */
    private boolean cache;

    /**
     * 是否缓存历史
     */
    private boolean history;

    /**
     * 数据表名称
     */
    private String table;

    /**
     * 版本
     */
    private String version;

    /**
     * 主键
     */
    private String[] pks;

    /**
     * 当前bean类
     */
    private Class<?> dataClass;

    /**
     * 属性
     */
    private Property[] properties;

    /**
     * 属性map[key=fieldName,value=Property]
     */
    private Map<String, Property> propMap = New.concurrentMap();


}
