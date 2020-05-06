package com.x.commons.util.proxy;

/**
 * @Desc TODO
 * @Date 2020-05-01 22:41
 * @Author AD
 */
public class Test {
    
    public void test() {
        System.out.println("hello world");
    }
    
    public static void main(String[] args) {
        Test proxy = Proxys.getProxy(Test.class,new IAop() {
            @Override
            public void before() throws Exception {
                System.out.println("before");
            }
    
            @Override
            public void after() throws Exception {
                System.out.println("after");
            }
        });
    }
    
    
}

