package com.x.commons.util.manager.test;

import com.x.commons.util.manager.Manager;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/5/13 18:27
 */
public class TestManager extends Manager<ITest, String> {

    private static TestManager manager = new TestManager(ITest.class);

    protected TestManager(Class<ITest> clazz) {
        super(clazz);
    }

    public static ITest getTest(String key) {
        return manager.get(key);
    }

    @Override
    protected void initFactory(ITest sub) {
        this.factory.put(sub.getKey(), sub);
    }

    public static void main(String[] args) {
        ITest a = TestManager.getTest("A");
        System.out.println(a.getValue());
    }
}
