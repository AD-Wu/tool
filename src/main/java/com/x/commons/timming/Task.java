package com.x.commons.timming;

/**
 * @Date 2019-10-17 22:13
 * @Author AD
 */
class Task implements Runnable{

    private Object lock = new Object();
    private Runnable task;
    private long runTime;
    private long period;
    private long repeatCount;
    private long currentCount;
    private boolean running;

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

    Runnable getTask() {
        return task;
    }

    boolean needToRun(long time) {
        if (this.running) {
            return false;
        } else {
            return this.runTime <= time;
        }
    }

    void setRunning(boolean running) {
        synchronized(this.lock) {
            this.running = running;
        }
    }

    void updateStatus() {
        synchronized(this.lock) {
            this.runTime += this.period;
            ++this.currentCount;
        }
    }

    boolean needRemove() {
        return this.repeatCount > 0L && this.currentCount >= this.repeatCount;
    }

    void updatePeriod(int period) {
        if ((long)period <= -this.period) {
            period = (int)(1L - this.period);
        }

        synchronized(this.lock) {
            this.runTime += (long)period;
            this.period += (long)period;
        }
    }

    void updateTime(int period) {
        synchronized(this.lock) {
            this.runTime += (long)period;
        }
    }

    @Override
    public void run() {
        try {
            task.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        synchronized(lock) {
            this.running = false;
        }
    }
}
