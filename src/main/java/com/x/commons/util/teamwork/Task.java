package com.x.commons.util.teamwork;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Desc 协作线程，团队工作，如：<br>
 * scan req thread -> req queue -> req run thread -> resp queue -> resp handle thread
 * @Date 2019-11-30 19:30
 * @Author AD
 */
public final class Task<T> {
    
    /**
     * 采用数组实现的有界阻塞线程安全队列。
     * 如果向已满的队列继续塞入元素，将当前的线程阻塞。
     * 如果向空队列获取元素，那么将当前线程阻塞。
     */
    private ArrayBlockingQueue<T> tasks;
    
    public Task(int capacity) {
        tasks = new ArrayBlockingQueue<>(capacity);
    }
    
    /**
     * poll(time)若不能立即取出，则可以等 time 参数规定的时间,取不到时返回 null
     * take：取对头，移除，若Queue为空，阻塞
     * peek: 取对头，但不移除
     *
     * @return
     */
    public T get() {
        try {
            T task = tasks.take();
            return task;
        } catch (InterruptedException e) {
            e.printStackTrace();
            notifyAll();
            return null;
        }
        
    }
    
    /**
     * add：加到BlockingQueue里，如果可以容纳，则返回 true，否则报异常
     * offer：加到BlockingQueue里，如果可以容纳，则返回 true，否则返回 false
     * put：加到BlockingQueue里，如果没有空间，则线程被阻塞，直到Queue有空间再继续
     *
     * @param req
     */
    public void put(T req) {
        try {
            tasks.put(req);
        } catch (InterruptedException e) {
            e.printStackTrace();
            notifyAll();
        }
    }
    
    public int size() {
        return tasks.size();
    }
    
    public void clear() {
        tasks.clear();
    }
    
}
