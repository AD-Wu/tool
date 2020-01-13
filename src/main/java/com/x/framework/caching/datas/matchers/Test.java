package com.x.framework.caching.datas.matchers;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/13 14:47
 */
public class Test {
    public static void main(String[] args) {
        Boolean a = new Boolean(true);
        Boolean b = new Boolean(true);
        boolean c = a;
        boolean d = b;
        boolean e = (c == d || c && !d);
        System.out.println(e);

    }
}
