package com.x.protocol.test.other;

import java.util.Arrays;

/**
 * @Date 2019-01-01 21:14
 * @Author AD
 */
public class Test {

    private int[] arr;

    public Test() {
        init();
        System.out.println(Arrays.toString(arr));
    }

    private void init() {
        int[] aa = {1, 2, 3};
        int i = 0;
        for (int a : aa) {
            arr[i++] = 5;
        }
    }

    public static void main(String[] args) {
        new Test();
    }

}
