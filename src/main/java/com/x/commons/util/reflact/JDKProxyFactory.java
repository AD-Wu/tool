package com.x.commons.util.reflact;

import com.x.commons.interfaces.IFactory;

/**
 * @Date 2019-03-04 20:26
 * @Author AD
 */
public class JDKProxyFactory<JDKProxy> implements IFactory<JDKProxy> {


    public static void main(String[] args) {
        final ITarget proxy = Proxys.getProxy(new Target(), ITarget.class);
        proxy.request();
        System.out.println(proxy.getClass());
        System.out.println(ITarget.class);
        System.out.println(new Target().getClass());
    }

    @Override
    public JDKProxy get() throws Exception {
        return null;
    }

}

interface ITarget{
    void request();
}

class Target implements ITarget{

    @Override
    public void request() {
        System.out.println("running...");
    }

}



