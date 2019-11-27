package com.x.commons.timming;

/**
 * 定时任务类
 *
 * @Date 2019-10-17 22:13
 * @Author AD
 */
class Task implements Runnable {
    
    /**
     * 对象锁
     */
    private Object lock = new Object();
    
    /**
     * 当前任务
     */
    private Runnable task;
    
    /**
     * 运行时间
     */
    private long runTime;
    
    /**
     * 周期
     */
    private long period;
    
    /**
     * 重复次数
     */
    private long repeatCount;
    
    /**
     * 当前运行次数
     */
    private long currentCount;
    
    /**
     * 是否运行的标识
     */
    private boolean running;
    
    /**
     * 任务对象构造方法
     *
     * @param task        可执行任务
     * @param delay       任务延迟多久开始执行，单位：毫秒
     * @param period      周期，单位：毫秒
     * @param repeatCount 重复次数，0表示无限
     */
    Task(Runnable task, long delay, long period, long repeatCount) {
        this.task = task;
        this.period = period < 1L ? 1L : period;
        this.repeatCount = repeatCount;
        if (delay > 0L) {
            this.runTime = System.currentTimeMillis() + delay;
        } else {
            this.runTime = System.currentTimeMillis();
        }
        
    }
    
    /**
     * 获取当前任务
     *
     * @return
     */
    Runnable getTask() {
        return task;
    }
    
    /**
     * 是否需要执行
     *
     * @param now 当前时间毫秒书
     *
     * @return true：需执行 false：不需执行
     */
    boolean needToRun(long now) {
        if (running) {
            return false;
        } else {
            return runTime <= now;
        }
    }
    
    /**
     * 设置运行状态
     *
     * @param running
     */
    void setRunning(boolean running) {
        synchronized (lock) {
            this.running = running;
        }
    }
    
    /**
     * 更新运行的次数并计算下一次执行时间
     */
    void updateStatus() {
        synchronized (lock) {
            runTime += period;
            ++currentCount;
        }
    }
    
    /**
     * 判断任务是否需要移除
     *
     * @return
     */
    boolean needRemove() {
        return repeatCount > 0L && currentCount >= repeatCount;
    }
    
    /**
     * 更新周期
     *
     * @param interval 周期间隔
     */
    void updatePeriod(int interval) {
        if ((long) interval <= -period) {
            interval = (int) (1L - period);
        }
        
        synchronized (lock) {
            runTime += (long) interval;
            period += (long) interval;
        }
    }
    
    /**
     * 更新下一次执行的时间
     *
     * @param period
     */
    void updateTime(int period) {
        synchronized (this.lock) {
            this.runTime += (long) period;
        }
    }
    
    @Override
    public void run() {
        try {
            task.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 一次任务执行完毕后需将运行状态设置为false
        synchronized (lock) {
            this.running = false;
        }
    }
    
}
