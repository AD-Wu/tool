package com.x.protocol.test;

import com.x.commons.util.bean.New;
import com.x.protocol.annotations.infoold.ActorInfo;
import com.x.protocol.annotations.infoold.DataInfo;
import com.x.protocol.annotations.infoold.MethodInfo;
import com.x.protocol.serialize.Serializer;
import com.x.protocol.test.actor.BActor;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @Date 2019-01-02 15:09
 * @Author AD
 */
public class Test {

    @SneakyThrows
    public static void main(String[] args) {
        // swapTest();
        annotationTest();
    }

    public static void swapTest() {
        Integer a = new Integer(3);
        Integer b = new Integer(7);
        System.out.println("a = " + a + ",b = " + b);
        swap(a, b);
        System.out.println("a = " + a + ",b = " + b);
    }
    @SneakyThrows
    private static void swap(Integer a, Integer b) {

    }

    @SneakyThrows
    public static void annotationTest() {
        byte[] bs = {3, 3, 3, 1, 1, 3, 3, 3, 2, 3, 3, 3, 1, 3, 3, 3, 1, 2, 2};
        final ActorInfo info = new ActorInfo(BActor.class);
        final DataInfo reqData = info.getReqData(info.getCmd(), info.getCtrls()[0]);
        System.out.println(reqData);

        Serializer serializer = new Serializer();
        Serializable req = serializer.serialize(reqData, New.buf(bs));
        System.out.println(req);

        final MethodInfo[] mis = info.getMethodInfos();
        for (MethodInfo mi : mis) {
            final Method m = mi.getMethod();
            m.invoke(info.getSelf(), req);
        }
    }


}
