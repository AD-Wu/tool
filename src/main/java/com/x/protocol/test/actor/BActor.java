package com.x.protocol.test.actor;

import com.x.protocol.anno.core.Actor;
import com.x.protocol.anno.core.Recv;
import com.x.protocol.test.bean.BData;

/**
 * @Date 2019-01-02 14:48
 * @Author AD
 */
@Actor(cmd = "B", doc = "B-Test")
public class BActor {

    @Recv(ctrl = "B1", doc = "M-B1", req = BData.class, rep = Void.class)
    public void b_test(BData b) {
        System.out.println("invoked");
        System.out.println(b);
    }

}
