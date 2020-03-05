package com.x.commons.util.teamwork;

import com.x.commons.util.bean.New;

import java.util.concurrent.*;

/**
 * @Desc TODO
 * @Date 2020-02-21 01:03
 * @Author AD
 */
public class Sort {
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor pool = New.threadPool();
        pool.submit(new Task());
        System.out.println("异步");
    }
    
    private static void testTrue(){
        System.out.println("true");
    }
    private static void testFalse(){
        System.out.println("false");
    }
    
    
    public static class Task implements Callable<Boolean>{
    
        @Override
        public Boolean call() throws Exception {
            TimeUnit.SECONDS.sleep(5);
            Sort.testTrue();
            return true;
        }
    
    }
    
}
