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
    
    @Override
    protected String[] getKeys(ITest sub) {
        return new String[]{sub.getKey()};
    }
    
    public static ITest get(String key) {
        return manager.getWorker(key);
    }
    
    public static void main(String[] args) {
        ITest a = TestManager.get("A");
        System.out.println(a.getValue());
    }
    
}
