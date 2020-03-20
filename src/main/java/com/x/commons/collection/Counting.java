package com.x.commons.collection;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 14:18
 */
public class Counting {
    private final AtomicInteger count;

    public Counting(int value) {
        this.count = new AtomicInteger(value);
    }

    public int add(int value) {
        return count.addAndGet(value);
    }

    public int addWithMaxCheck(int value, int max) {
        int result = add(value);
        if (result > max) {
            count.set(max);
        }
        return count.get();
    }

    public int addWithMinCheck(int value, int min) {
        int result = add(value);
        if (result < min) {
            count.set(min);
        }
        return count.get();
    }

    public void set(int value) {
        count.set(value);
    }

    public int getCount() {
        return count.get();
    }
}
