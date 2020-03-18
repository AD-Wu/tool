package com.x.protocol.layers.application;

import com.x.commons.util.bean.New;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.DataConfig;
import com.x.protocol.core.DataInfo;
import com.x.protocol.core.IProtocol;
import com.x.protocol.layers.application.config.ActorsConfig;
import com.x.protocol.layers.application.config.ApplicationConfig;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @Desc TODO
 * @Date 2020-03-08 19:57
 * @Author AD
 */
public class Application {

    // ------------------------ 变量定义 ------------------------
    private Map<String, DataInfo> fromRemoteRequest = New.concurrentMap();

    private Map<String, DataInfo> fromRemoteResponse = New.concurrentMap();

    private Set<String> fromRemoteCmdNoCtrl = New.concurrentSet();

    private Map<String, DataInfo> toRemoteRequest = New.concurrentMap();

    private Map<String, DataInfo> toRemoteResponse = New.concurrentMap();

    private Set<String> toRemoteCmdNoCtrl = New.concurrentSet();

    private Map<Class<?>, DataConfig> dataConfigs = New.concurrentMap();

    private Map<Class<?>, String> actorCmdMap = New.concurrentMap();

    // ------------------------ 构造方法 ------------------------
    public Application(IProtocol protocol, ApplicationConfig appConfig) throws Exception {
        ActorsConfig actorsConf = appConfig.getActors();
        if (actorsConf != null) {

        }
    }


    // ------------------------ 方法定义 ------------------------

    public String getActorCommand(Class<?> clazz) {
        return actorCmdMap.get(clazz);
    }

    public DataInfo getFromRemoteRequestConfig(ChannelData data) {
        return fromRemoteRequest.get(data.getChannelKey());
    }

    public DataInfo getFromRemoteResponseConfig(ChannelData data) {
        return fromRemoteResponse.get(data.getChannelKey());
    }

    public DataInfo getToRemoteRequestConfig(ChannelData reqData) {
        return toRemoteRequest.get(reqData.getChannelKey());
    }

    public DataInfo getToRemoteResponseConfig(ChannelData data) {
        return toRemoteResponse.get(data.getChannelKey());
    }

    public boolean hasFromRemoteControlKey(String key) {
        return !fromRemoteCmdNoCtrl.contains(key);
    }

    public boolean hasToRemoteControlKey(String key) {
        return !toRemoteCmdNoCtrl.contains(key);
    }

    public DataConfig getDataConfig(Class<?> clazz) {
        return dataConfigs.get(clazz);
    }

    // ------------------------ 私有方法 ------------------------

    private boolean addActorClass(Map<String, DataInfo> infos, String key, Class<?> actorClass) {
        DataInfo dataInfo = infos.get(key);
        Class<?>[] actors = dataInfo.getActors();

        for (Class<?> actor : actors) {
            if (actor.equals(actorClass)) {
                return false;
            }
        }
        Class<?>[] copys = new Class<?>[actors.length + 1];
        System.arraycopy(actors, 0, copys, 0, actors.length);
        copys[actors.length] = actorClass;
        dataInfo.setActors(copys);
        return true;
    }

    private Class<?> getParameterType(Class<?> clazz1, Class<?> clazz2,
                                      Class<?>[] classes, Type[] types) {
        if (classes.length != 1) return null;
        Class<?> clazz = classes[0];
        return !isVoid(clazz) ? Annotations.getActualType(clazz1, clazz2, clazz, types[0]) : null;
    }

    private DataConfig getTypeDataConfig(Class<?> clazz) {
        if (clazz == null) return null;
        if (!isVoid(clazz)) {
            return clazz.isArray() ? dataConfigs.get(clazz.getComponentType()) : dataConfigs.get(
                    clazz);
        }
        return null;
    }

    private boolean isVoid(Class<?> clazz) {
        return "void".equals(clazz.getName()) || Void.class.equals(clazz);
    }
}
