package com.x.commons.timming;

import com.x.commons.util.date.DateTimes;

import java.util.concurrent.TimeUnit;

/**
 * @Desc TODO
 * @Date 2019-11-27 21:26
 * @Author AD
 */
class Test {
    
    public static void main(String[] args) {
        Timer timer = Timer.get();
        timer.add(()->{
            System.out.println("1-->"+DateTimes.now());
            try {
                TimeUnit.SECONDS.sleep(1);
                int a = 3/0;
            } catch (Exception e) {
                System.out.println("1-->"+e.getMessage());
            }
        },3, TimeUnit.SECONDS);
        
        timer.add(()->{
            System.out.println("2-->"+DateTimes.now());
            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 3, TimeUnit.SECONDS);
    }
}
