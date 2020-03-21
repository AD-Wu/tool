package com.x.protocol.core;

import com.x.commons.collection.DataSet;
import com.x.commons.util.string.Strings;
import com.x.protocol.enums.ProtocolStatus;

import java.io.Serializable;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/9 15:06
 */
public class ChannelData {
    // ------------------------ 变量定义 ------------------------
    
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
    
    private boolean noCtrlCallbackKey = false;
    
    // ------------------------ 构造方法 ------------------------
    
    private ChannelData() {}
    
    // ------------------------ 方法定义 ------------------------
    public static ChannelData fromRemoteRequest(
            String command, String control,
            String version, int mappingIndex,
            Serializable dataSerialized, DataSet dataSet) {
        // commandKey,channelKey
        ChannelData data = get(command, control, version, true);
        data.mappingIndex = mappingIndex;
        data.callbackKey = data.mappingIndex + "|" + data.channelKey;
        data.dataSerialized = dataSerialized;
        data.dataSet = dataSet;
        return data;
    }
    
    public static ChannelData fromRemoteResponse(
            String command, String control, String version,
            int mappingIndex, Serializable dataSerialized, DataSet dataSet,
            int status, String msg) {
        ChannelData data = get(command, control, version, false);
        data.mappingIndex = mappingIndex;
        data.callbackKey = data.mappingIndex + "|" + data.channelKey;
        data.dataSerialized = dataSerialized;
        data.status = status;
        data.message = msg;
        data.dataSet = dataSet;
        return data;
    }
    
    public static ChannelData toRemoteRequest(
            String command, String control, String version,
            Serializable dataBean, DataSet dataSet) {
        ChannelData data = get(command, control, version, true);
        data.callbackKey = "|" + data.channelKey;
        data.dataBean = dataBean;
        data.dataSet = dataSet;
        return data;
    }
    
    public static ChannelData protocolToRemoteRequest(
            ChannelData channelData, ChannelInfo channelInfo,
            DataInfo dataInfo, Serializable dataSerialized) {
        ChannelData data = new ChannelData();
        data.request = true;
        data.command = channelData.command;
        data.control = channelData.control;
        data.version = channelData.version;
        data.mappingIndex = channelInfo.getChannel().getNextMappingIndex();
        data.commandKey = channelData.commandKey;
        data.channelKey = channelData.channelKey;
        data.callbackKey = data.mappingIndex + channelData.callbackKey;
        data.noCtrlCallbackKey = channelData.noCtrlCallbackKey;
        data.dataInfo = dataInfo;
        data.dataSerialized = dataSerialized;
        data.dataBean = channelData.dataBean;
        
        data.dataSet = channelData.dataSet;
        return data;
    }
    
    public static ChannelData protocolToRemoteResponse(
            ChannelData channelData, DataInfo dataInfo, Serializable dataBean,
            Serializable dataSerialized, DataSet dataSet, int status, String msg) {
        ChannelData data = new ChannelData();
        data.request = false;
        data.command = channelData.command;
        data.control = channelData.control;
        data.version = channelData.version;
        data.mappingIndex = channelData.mappingIndex;
        data.commandKey = channelData.commandKey;
        data.channelKey = channelData.channelKey;
        data.noCtrlCallbackKey = channelData.noCtrlCallbackKey;
        data.status = status;
        data.message = msg;
        data.dataInfo = dataInfo;
        data.dataBean = dataBean;
        data.dataSerialized = dataSerialized;
        data.dataSet = dataSet;
        return data;
    }
    
    /**
     * 生成cmd,ctrl,version,request,commandKey,channelKey
     *
     * @param cmd     命令字
     * @param ctrl    控制字
     * @param version 版本
     * @param request 是否请求
     *
     * @return
     */
    private static ChannelData get(String cmd, String ctrl, String version, boolean request) {
        ChannelData data = new ChannelData();
        data.request = request;
        data.command = Strings.fixNull(cmd);
        data.control = Strings.fixNull(ctrl);
        data.version = Strings.fixNull(version);
        data.commandKey = data.command + (Strings.isNull(data.control) ? "" : ":" + data.control);
        data.channelKey = data.command + "|" + data.control + "|" + data.version;
        return data;
    }
    
    // ---------------------- get and set ----------------------
    public void setNoCtrlCallbackKey() {
        this.noCtrlCallbackKey = true;
        this.callbackKey = "|" + command + "||" + version;
    }
    
    public String getNoCtrlCallbackKey() {
        return mappingIndex + "|" + command + "||" + version;
    }
    
    public void setControl(String control) {
        this.control = Strings.fixNull(control);
        this.commandKey = command + "|" + (Strings.isNull(control) ? "" : control);
        this.channelKey = command + "|" + control + "|" + version;
    }
    
    public boolean isSucceed() {
        return this.status == ProtocolStatus.OK;
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
    
    public <T extends Serializable> T getDataBean() {
        return (T) dataBean;
    }
    
    public void setDataBean(Serializable dataBean) {
        this.dataBean = dataBean;
    }
    
    public <T extends Serializable> T getDataSerialized() {
        return (T) dataSerialized;
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
    
}
