package com.x.protocol.anno.info;

import com.x.commons.util.bean.New;
import com.x.commons.util.reflact.Methods;
import com.x.protocol.anno.core.Actor;
import com.x.protocol.anno.core.Recv;
import com.x.protocol.anno.core.Send;
import lombok.Data;
import lombok.NonNull;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @Date 2018-12-31 22:20
 * @Author AD
 */
@Data
public final class ActorInfo {

    private String cmd;
    private String doc;
    private short systemID;

    private Class<?> selfClass;
    private Object self;
    private MethodInfo[] methodInfos;

    private Map<String, MethodInfo> map;

    public ActorInfo(@NonNull Class<?> target) {
        if (target.isAnnotationPresent(Actor.class)) {
            final Actor a = target.getAnnotation(Actor.class);
            this.cmd = a.cmd();
            this.doc = a.doc();
            this.systemID = a.systemID();
            this.selfClass = target;
            try {
                this.self = selfClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            this.init();
        }
    }

    public DataInfo getReqData(String cmd, String ctrl) {
        return map.get(cmd.concat("|").concat(ctrl)).getReqData();
    }

    public FieldInfo[] getFieldInfos(String cmd, String ctrl) {
        DataInfo reqData = map.get(cmd.concat("|").concat(ctrl)).getReqData();
        return map.get(cmd.concat("|").concat(ctrl)).getReqData().getFieldInfos();
    }

    public String[] getCtrls() {
        return Stream.of(methodInfos).map(m -> m.getCtrl()).collect(toList()).toArray(new String[0]);
    }

    private void init() {
        final Method[] ms = Methods.getMethods(selfClass, Recv.class, Send.class);
        MethodInfo[] infos = new MethodInfo[ms.length];
        map = New.map(ms.length);
        for (int i = 0, c = ms.length; i < c; ++i) {
            Method m = ms[i];
            MethodInfo info = new MethodInfo(selfClass, m);
            infos[i] = info;
            map.put(cmd.concat("|").concat(info.getCtrl()), info);
        }
        methodInfos = infos;
    }

}
