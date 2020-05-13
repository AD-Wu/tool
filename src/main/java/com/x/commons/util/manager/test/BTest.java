package com.x.commons.util.manager.test;

import com.google.auto.service.AutoService;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/5/13 18:27
 */
@AutoService(ITest.class)
public class BTest implements ITest {
    @Override
    public String getValue() {
        return "B:Hello World";
    }

    @Override
    public String getKey() {
        return "B";
    }
}
