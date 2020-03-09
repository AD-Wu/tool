package com.x.protocol.core;

import com.x.commons.collection.DataSet;

import java.io.Serializable;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/9 15:06
 */
public class ChannelData {
    private String command;

    private String control;

    private boolean request;

    private String version;

    private int mappingIndex;

    private int status = 1;

    private String message;

    private DataInfo dataInfo;

    private Serializable dataBean;

    private Serializable dataSerialized;

    private DataSet dataSet;

    private String commandKey;

    private String channelKey;

    private String callbackKey;

    private boolean noCtrlCallbackKey;

    public ChannelData() {
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getMappingIndex() {
        return mappingIndex;
    }

    public void setMappingIndex(int mappingIndex) {
        this.mappingIndex = mappingIndex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataInfo getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(DataInfo dataInfo) {
        this.dataInfo = dataInfo;
    }

    public Serializable getDataBean() {
        return dataBean;
    }

    public void setDataBean(Serializable dataBean) {
        this.dataBean = dataBean;
    }

    public Serializable getDataSerialized() {
        return dataSerialized;
    }

    public void setDataSerialized(Serializable dataSerialized) {
        this.dataSerialized = dataSerialized;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public String getCommandKey() {
        return commandKey;
    }

    public void setCommandKey(String commandKey) {
        this.commandKey = commandKey;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public String getCallbackKey() {
        return callbackKey;
    }

    public void setCallbackKey(String callbackKey) {
        this.callbackKey = callbackKey;
    }

    public boolean isNoCtrlCallbackKey() {
        return noCtrlCallbackKey;
    }

    public void setNoCtrlCallbackKey(boolean noCtrlCallbackKey) {
        this.noCtrlCallbackKey = noCtrlCallbackKey;
    }
}
