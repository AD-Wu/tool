package com.x.commons.util.manager.test;

import com.google.auto.service.AutoService;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/5/13 18:26
 */
@AutoService(ITest.class)
public class ATest implements ITest {
    @Override
    public String getValue() {
        return "A:Hello World";
    }

    @Override
    public String getKey() {
        return "A";
    }
}
