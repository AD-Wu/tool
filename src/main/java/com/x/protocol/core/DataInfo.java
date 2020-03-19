package com.x.protocol.core;

import com.x.commons.util.string.Strings;

import java.lang.reflect.Method;

/**
 * @Desc TODO
 * @Date 2020-03-08 21:49
 * @Author AD
 */
public class DataInfo {
    private String command;

    private String control;

    private String version;

    private String doc;

    private boolean skipLogin;

    private boolean debugInfo;

    private short systemID;

    private short moduleID;

    private short licenseID;

    private Method method;

    private Class<?>[] actors;

    private Class<?> dataClass;

    private DataConfig dataConfig;

    /**
     * 数据信息对象
     *
     * @param skipLogin 是否跳过登录
     * @param debugInfo 是否调试
     * @param systemID  系统ID
     * @param moduleID  模块ID
     * @param licenseID 许可ID
     */
    public DataInfo(boolean skipLogin, boolean debugInfo, short systemID, short moduleID, short licenseID) {
        this.skipLogin = skipLogin;
        this.debugInfo = debugInfo;
        this.systemID = systemID;
        this.moduleID = moduleID;
        this.licenseID = licenseID;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = Strings.fixNull(command, true);
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = Strings.fixNull(control, true);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = Strings.fixNull(version, true);
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public boolean isSkipLogin() {
        return skipLogin;
    }

    public void setSkipLogin(boolean skipLogin) {
        this.skipLogin = skipLogin;
    }

    public boolean isDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(boolean debugInfo) {
        this.debugInfo = debugInfo;
    }

    public short getSystemID() {
        return systemID;
    }

    public void setSystemID(short systemID) {
        this.systemID = systemID;
    }

    public short getModuleID() {
        return moduleID;
    }

    public void setModuleID(short moduleID) {
        this.moduleID = moduleID;
    }

    public short getLicenseID() {
        return licenseID;
    }

    public void setLicenseID(short licenseID) {
        this.licenseID = licenseID;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?>[] getActors() {
        return actors;
    }

    public void setActors(Class<?>[] actors) {
        this.actors = actors;
    }

    public Class<?> getDataClass() {
        return dataClass;
    }

    public void setDataClass(Class<?> dataClass) {
        this.dataClass = dataClass;
    }

    public DataConfig getDataConfig() {
        return dataConfig;
    }

    public void setDataConfig(DataConfig dataConfig) {
        this.dataConfig = dataConfig;
    }
}
