package com.x.protocol.layers.application;

import com.x.commons.events.Dispatcher;
import com.x.commons.util.bean.New;
import com.x.commons.util.string.Strings;
import com.x.protocol.annotations.XActor;
import com.x.protocol.annotations.XAuto;
import com.x.protocol.annotations.XRecv;
import com.x.protocol.annotations.XSend;
import com.x.protocol.core.*;
import com.x.protocol.layers.application.config.*;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
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
        ActorsConfig actorsConfig = appConfig.getActors();
        if (actorsConfig != null) {
            List<Class<?>> actors = New.list();
            List<Class<?>> readyActors = New.list();
            List<Class<?>> interfaces = New.list();
            boolean disableDoc = true;
            Class<?> dftActorClass = null;
            ActorSetting setting = actorsConfig.getSetting();
            if (setting != null) {
                disableDoc = setting.isDisableDoc();
                String dftActor = setting.getDefaultActor();
                if (Strings.isNotNull(dftActor)) {
                    dftActorClass = Class.forName(dftActor);
                }
            }

            List<PackageConfig> packages = actorsConfig.getPackages();
            if (packages != null && packages.size() > 0) {
                Iterator<PackageConfig> it = packages.iterator();

                while (it.hasNext()) {
                    PackageConfig pkg = it.next();
                    // 扫描包信息
                    AnnotationInfo info = Annotations.getAnnotationInfo(pkg.getBase(),
                                                                        pkg.getName(), disableDoc);
                    this.dataConfigs.putAll(info.getDataConfigs());
                    actors.addAll(info.getActors());
                    readyActors.addAll(info.getReadyActors());
                    interfaces.addAll(info.getInterfaces());
                }
            }

            Map<Class<?>, List<DataInfo>> infoMap = New.map();
            Iterator<Class<?>> it = actors.iterator();

            Class<?> actorClass;
            while (it.hasNext()) {
                actorClass = it.next();
                this.analyzeActors(actorClass, actorClass, disableDoc, infoMap);
            }

            it = interfaces.iterator();

            while (it.hasNext()) {
                actorClass = it.next();
                boolean var14 = false;
                Iterator<Class<?>> readyIt = readyActors.iterator();

                while (readyIt.hasNext()) {
                    Class<?> readyClass = readyIt.next();
                    if (actorClass.isAssignableFrom(readyClass)) {
                        this.analyzeActors(actorClass, readyClass, disableDoc, infoMap);
                        if (!var14) {
                            var14 = true;
                        }
                    }
                }

                if (!var14 && dftActorClass != null) {
                    this.analyzeActors(actorClass, dftActorClass, disableDoc, infoMap);
                }
            }
        }

        List<ListenerConfig> configs = appConfig.getListeners();
        if (configs != null && configs.size() > 0) {
            Dispatcher dispatcher = protocol.getDispatcher();
            Iterator<ListenerConfig> it = configs.iterator();

            label71:
            while (it.hasNext()) {
                ListenerConfig config = it.next();
                List<Class<?>> listeners = ListenerHelper.getListeners(config);
                Iterator<Class<?>> listenerIt = listeners.iterator();

                while (true) {
                    Class<?> listener;
                    EventInfo[] eventInfos;
                    do {
                        do {
                            if (!listenerIt.hasNext()) {
                                continue label71;
                            }

                            listener = listenerIt.next();
                            IProtocolListener prtcListener = (IProtocolListener) listener.newInstance();
                            eventInfos = prtcListener.getEventInfos();
                        } while (eventInfos == null);
                    } while (eventInfos.length <= 0);

                    for (EventInfo eventInfo : eventInfos) {
                        dispatcher.addListener(eventInfo.getType(), listener, 0,
                                               eventInfo.getParams());
                    }
                }
            }
        }

    }

    private void analyzeActors(Class<?> actorClass, Class<?> readyClass, boolean disableDoc, Map<Class<?>, List<DataInfo>> infoMap) throws Exception {
        // 获取XActor注解
        XActor xActor = actorClass.getAnnotation(XActor.class);
        // 检查非空数据
        Annotations.checkError(actorClass, xActor, "doc", xActor.doc(), disableDoc);
        Annotations.checkError(actorClass, xActor, "cmd", xActor.cmd(), true);
        // 获取所有public方法
        Method[] methods = actorClass.getMethods();
        // 将已准备好的类存入map
        String cmd = xActor.cmd();
        short systemID = xActor.systemID();
        this.actorCmdMap.put(readyClass, cmd);

        // 遍历方法
        for (Method method : methods) {
            Class<?> param;
            DataConfig dataConfig;
            String ctrl;
            String version;
            String key;
            DataInfo dataInfo;
            if (method.isAnnotationPresent(XRecv.class)) {
                XRecv xRecv = method.getAnnotation(XRecv.class);
                Annotations.checkError(actorClass, xRecv, "doc", xRecv.doc(), disableDoc);
                param = xRecv.req();
                if (XAuto.class.equals(param)) {
                    param = this.getParameterType(actorClass, readyClass,
                                                  method.getParameterTypes(),
                                                  method.getGenericParameterTypes());
                }

                if (Void.class.equals(param)) {
                    param = null;
                }

                dataConfig = this.getTypeDataConfig(param);
                ctrl = xRecv.ctrl();
                version = dataConfig == null ? "1" : dataConfig.getVersion();
                key = cmd + "|" + ctrl + "|" + version;
                if (fromRemoteRequest.containsKey(key)) {
                    this.addActorClass(fromRemoteRequest, key, readyClass);
                } else {
                    if (ctrl.length() == 0) {
                        fromRemoteCmdNoCtrl.add(cmd);
                    }

                    dataInfo = new DataInfo(xRecv.skipLogin(), xRecv.debug(), systemID,
                                            xRecv.moduleID(), xRecv.licenseID());
                    dataInfo.setCommand(cmd);
                    dataInfo.setControl(ctrl);
                    dataInfo.setVersion(version);
                    dataInfo.setDoc(xActor.doc() + ":" + xRecv.doc());
                    dataInfo.setMethod(method);
                    dataInfo.setActors(new Class[]{readyClass});
                    dataInfo.setDataClass(param);
                    dataInfo.setDataConfig(dataConfig);
                    fromRemoteRequest.put(key, dataInfo);
                }

                param = xRecv.resp();
                if (XAuto.class.equals(param)) {
                    param = this.getParameterType(actorClass, readyClass,
                                                  new Class[]{method.getReturnType()},
                                                  new Type[]{method.getGenericReturnType()});
                }

                if (Void.class.equals(param)) {
                    param = null;
                }

                dataConfig = this.getTypeDataConfig(param);
                version = dataConfig == null ? "1" : dataConfig.getVersion();
                key = cmd + "|" + ctrl + "|" + version;
                if (this.fromRemoteResponse.containsKey(key)) {
                    this.addActorClass(fromRemoteResponse, key, readyClass);
                } else {
                    dataInfo = new DataInfo(false, xRecv.debug(), systemID,
                                            xRecv.moduleID(), xRecv.licenseID());
                    dataInfo.setCommand(cmd);
                    dataInfo.setControl(ctrl);
                    dataInfo.setVersion(version);
                    dataInfo.setDoc(xActor.doc() + ":" + xRecv.doc());
                    dataInfo.setMethod(method);
                    dataInfo.setActors(new Class[]{readyClass});
                    dataInfo.setDataClass(param);
                    dataInfo.setDataConfig(dataConfig);
                    this.fromRemoteResponse.put(key, dataInfo);
                }
            } else if (method.isAnnotationPresent(XSend.class)) {
                XSend xSend = method.getAnnotation(XSend.class);
                Annotations.checkError(actorClass, xSend, "doc", xSend.doc(), disableDoc);
                param = xSend.req();
                if (XAuto.class.equals(param)) {
                    param = this.getParameterType(actorClass, readyClass,
                                                  method.getParameterTypes(),
                                                  method.getGenericParameterTypes());
                }

                if (Void.class.equals(param)) {
                    param = null;
                }

                dataConfig = this.getTypeDataConfig(param);
                ctrl = xSend.ctrl();
                version = dataConfig == null ? "1" : dataConfig.getVersion();
                key = cmd + "|" + ctrl + "|" + version;
                if (toRemoteRequest.containsKey(key)) {
                    this.addActorClass(toRemoteRequest, key, readyClass);
                } else {
                    if (ctrl.length() == 0) {
                        this.toRemoteCmdNoCtrl.add(cmd);
                    }

                    dataInfo = new DataInfo(false, xSend.debug(), (short) 0, (short) 0, (short) 0);
                    dataInfo.setCommand(cmd);
                    dataInfo.setControl(ctrl);
                    dataInfo.setVersion(version);
                    dataInfo.setDoc(xActor.doc() + ":" + xSend.doc());
                    dataInfo.setMethod(method);
                    dataInfo.setActors(new Class[]{readyClass});
                    dataInfo.setDataClass(param);
                    dataInfo.setDataConfig(dataConfig);
                    this.toRemoteRequest.put(key, dataInfo);
                    if (param != null) {
                        if (infoMap.containsKey(param)) {
                            infoMap.get(param).add(dataInfo);
                        } else {
                            List<DataInfo> dataInfos = New.list();
                            dataInfos.add(dataInfo);
                            infoMap.put(param, dataInfos);
                        }
                    }
                }

                param = xSend.resp();
                if (XAuto.class.equals(param)) {
                    param = this.getParameterType(actorClass, readyClass,
                                                  new Class[]{method.getReturnType()},
                                                  new Type[]{method.getGenericReturnType()});
                }

                if (Void.class.equals(param)) {
                    param = null;
                }

                dataConfig = this.getTypeDataConfig(param);
                version = dataConfig == null ? "1" : dataConfig.getVersion();
                key = cmd + "|" + ctrl + "|" + version;
                if (toRemoteResponse.containsKey(key)) {
                    this.addActorClass(toRemoteResponse, key, readyClass);
                } else {
                    dataInfo = new DataInfo(false, xSend.debug(), (short) 0, (short) 0, (short) 0);
                    dataInfo.setCommand(cmd);
                    dataInfo.setControl(ctrl);
                    dataInfo.setVersion(version);
                    dataInfo.setDoc(xActor.doc() + ":" + xSend.doc());
                    dataInfo.setMethod(method);
                    dataInfo.setActors(new Class[]{readyClass});
                    dataInfo.setDataClass(param);
                    dataInfo.setDataConfig(dataConfig);
                    this.toRemoteResponse.put(key, dataInfo);
                }
            }
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

    private Class<?> getParameterType(Class<?> actorClass, Class<?> readyClass,
                                      Class<?>[] methodParamTypes, Type[] types) {
        if (methodParamTypes.length != 1) return null;
        Class<?> methodParamType = methodParamTypes[0];
        return !isVoid(methodParamType) ? Annotations.getActualType(actorClass, readyClass,
                                                                    methodParamType,
                                                                    types[0]) : null;
    }

    private DataConfig getTypeDataConfig(Class<?> clazz) {
        if (clazz == null) return null;
        if (!isVoid(clazz)) {
            return clazz.isArray() ?
                    dataConfigs.get(clazz.getComponentType()) : dataConfigs.get(clazz);
        }
        return null;
    }

    private boolean isVoid(Class<?> clazz) {
        return "void".equals(clazz.getName()) || Void.class.equals(clazz);
    }
}
