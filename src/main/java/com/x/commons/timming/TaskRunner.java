package com.x.commons.timming;

/**
 * @Date 2019-10-17 22:13
 * @Author AD
 */
class TaskRunner implements Runnable{

    private Object lock = new Object();
    private Runnable runner;
    private long runTime;
    private long interval;
    private long repeatCount;
    private long currentCount;
    private boolean running;

    TaskRunner(Runnable var1, long var2, long var4, long var6) {
        this.runner = var1;
        this.interval = var4 < 1L ? 1L : var4;
        this.repeatCount = var6;
        if (var2 > 0L) {
            this.runTime = System.currentTimeMillis() + var2;
        } else {
            this.runTime = System.currentTimeMillis();
        }

    }

    Runnable getRunner() {
        return this.runner;
    }

    boolean needToRun(long var1) {
        if (this.running) {
            return false;
        } else {
            return this.runTime <= var1;
        }
    }

    void setRunning(boolean var1) {
        synchronized(this.lock) {
            this.running = var1;
        }
    }

    void updateStatus() {
        synchronized(this.lock) {
            this.runTime += this.interval;
            ++this.currentCount;
        }
    }

    boolean needRemove() {
        return this.repeatCount > 0L && this.currentCount >= this.repeatCount;
    }

    void updateInterval(int var1) {
        if ((long)var1 <= -this.interval) {
            var1 = (int)(1L - this.interval);
        }

        synchronized(this.lock) {
            this.runTime += (long)var1;
            this.interval += (long)var1;
        }
    }

    void updateTime(int var1) {
        synchronized(this.lock) {
            this.runTime += (long)var1;
        }
    }

    @Override
    public void run() {
        try {
            this.runner.run();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        synchronized(this.lock) {
            this.running = false;
        }
    }
}
