package com.x.commons.util.manager.test;

import com.x.commons.util.manager.Manager;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/5/13 18:27
 */
public class TestManager extends Manager<ITest, String> {
    
    private static TestManager manager = new TestManager();
    
    public static ITest get(String key) {
        return manager.getWorker(key);
    }
    
    @Override
    protected Class<ITest> getClazz() {
        return ITest.class;
    }
    
    @Override
    protected String[] getKeys(ITest sub) {
        return new String[]{sub.getKey()};
    }
    
    public static void main(String[] args) {
        ITest a = TestManager.get("A");
        System.out.println(a.getValue());
    }
    
}
