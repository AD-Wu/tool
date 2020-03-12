package com.x.protocol.anno.infoold;

import com.x.protocol.anno.coreold.Actor;
import com.x.protocol.anno.coreold.Recv;
import com.x.protocol.anno.coreold.Send;
import lombok.Data;
import lombok.NonNull;

import java.lang.reflect.Method;

/**
 * @Date 2019-01-01 22:59
 * @Author AD
 */
@Data
public final class MethodInfo {

    private String ctrl;
    private String doc;
    private Class<?> req;
    private Class<?> rep;
    private boolean skipLogin;
    private boolean debugInfo;
    private short moduleID;
    private short licenseID;

    private Method method;
    private DataInfo reqData;
    private DataInfo repData;

    public MethodInfo(@NonNull Class<?> target, @NonNull Method method) {
        if (target.isAnnotationPresent(Actor.class)) {
            if (method.isAnnotationPresent(Recv.class)) {
                initRecv(method);
            }
            if (method.isAnnotationPresent(Send.class)) {
                initSend(method);
            }
        }
    }

    private void initRecv(Method method) {
        final Recv recv = method.getAnnotation(Recv.class);
        ctrl = recv.ctrl();
        doc = recv.doc();
        req = recv.req();
        rep = recv.rep();
        skipLogin = recv.skipLogin();
        debugInfo = recv.debugInfo();
        moduleID = recv.moduleID();
        licenseID = recv.licenseID();
        this.method = method;
        reqData = new DataInfo(req);
        repData = new DataInfo(rep);
    }

    private void initSend(Method method) {
        final Send send = method.getAnnotation(Send.class);
        this.ctrl = send.ctrl();
        this.doc = send.doc();
        this.req = send.req();
        this.rep = send.rep();
        this.debugInfo = send.debugInfo();
        this.moduleID = send.moduleID();
        this.licenseID = send.licenseID();
        this.method = method;
        this.reqData = new DataInfo(req);
        this.repData = new DataInfo(rep);
    }


}
