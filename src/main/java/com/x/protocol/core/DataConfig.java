package com.x.protocol.core;

import com.x.commons.util.bean.New;

import java.util.Map;

/**
 * @Desc
 * @Date 2020-01-19 21:54
 * @Author AD
 */
public class DataConfig {
    // ------------------------ 变量定义 ------------------------
    
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
    
    // ------------------------ 构造方法 ------------------------
    public DataConfig() {}
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 获取bean说明
     *
     * @return String bean说明
     */
    public String getDoc() {
        return this.doc;
    }
    
    /**
     * 设置bean说明
     *
     * @param doc bean说明
     */
    public void setDoc(String doc) {
        this.doc = doc;
    }
    
    /**
     * 获取是否缓存
     *
     * @return boolean 是否缓存
     */
    public boolean isCache() {
        return this.cache;
    }
    
    /**
     * 设置是否缓存
     *
     * @param cache 是否缓存
     */
    public void setCache(boolean cache) {
        this.cache = cache;
    }
    
    /**
     * 获取是否缓存历史
     *
     * @return boolean 是否缓存历史
     */
    public boolean isHistory() {
        return this.history;
    }
    
    /**
     * 设置是否缓存历史
     *
     * @param history 是否缓存历史
     */
    public void setHistory(boolean history) {
        this.history = history;
    }
    
    /**
     * 获取数据表名称
     *
     * @return String 数据表名称
     */
    public String getTable() {
        return this.table;
    }
    
    /**
     * 设置数据表名称
     *
     * @param table 数据表名称
     */
    public void setTable(String table) {
        this.table = table;
    }
    
    /**
     * 获取版本
     *
     * @return String 版本
     */
    public String getVersion() {
        return this.version;
    }
    
    /**
     * 设置版本
     *
     * @param version 版本
     */
    public void setVersion(String version) {
        this.version = version;
    }
    
    /**
     * 获取主键
     *
     * @return String[] 主键
     */
    public String[] getPks() {
        return this.pks;
    }
    
    /**
     * 设置主键
     *
     * @param pks 主键
     */
    public void setPks(String[] pks) {
        this.pks = pks;
    }
    
    /**
     * 获取当前bean类
     *
     * @return Class<?> 当前bean类
     */
    public Class<?> getDataClass() {
        return this.dataClass;
    }
    
    /**
     * 设置当前bean类
     *
     * @param dataClass 当前bean类
     */
    public void setDataClass(Class<?> dataClass) {
        this.dataClass = dataClass;
    }
    
    /**
     * 获取属性
     *
     * @return Property[] 属性
     */
    public Property[] getProperties() {
        return this.properties;
    }
    
    /**
     * 设置属性
     *
     * @param properties 属性
     */
    public void setProperties(Property[] properties) {
        this.properties = properties;
    }
    
    /**
     * 获取属性map[key=fieldName,value=Property]
     *
     * @return Map<String       ,               Property> 属性map[key=fieldName,value=Property]
     */
    public Map<String, Property> getPropMap() {
        return this.propMap;
    }
    
    /**
     * 设置属性map[key=fieldName,value=Property]
     *
     * @param propMap 属性map[key=fieldName,value=Property]
     */
    public void setPropMap(Map<String, Property> propMap) {
        this.propMap = propMap;
    }
    
}
