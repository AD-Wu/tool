package com.x.protocol.layers.transport.channel.core;

import com.x.commons.collection.DataSet;
import com.x.commons.util.string.Strings;

/**
 * @Desc
 * @Date 2020-03-10 00:25
 * @Author AD
 */
class ClientParameter {
    
    // ------------------------ 变量定义 ------------------------
    
    private String name;
    
    private DataSet dataSet;
    
    private boolean loggedError;
    
    // ------------------------ 构造方法 ------------------------
    ClientParameter() {}
    // ------------------------ 方法定义 ------------------------
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public DataSet getDataSet() {
        return dataSet;
    }
    
    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }
    
    public boolean isLoggedError() {
        return loggedError;
    }
    
    public void setLoggedError(boolean loggedError) {
        this.loggedError = loggedError;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
