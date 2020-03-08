package com.x.protocol.layers.session;

import com.x.commons.util.bean.New;

import java.util.List;

/**
 * @Desc
 * @Date 2020-03-08 22:21
 * @Author AD
 */
public class SessionChannelInfo {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 远端IP
     */
    private final String remoteIP;
    
    /**
     * 计数时间
     */
    private List<Long> countTimes = New.list();
    
    /**
     * 检查时间
     */
    private long checkTime = 0L;
    
    // ------------------------ 构造方法 ------------------------
    public SessionChannelInfo(String remoteIP) {
        this.remoteIP = remoteIP;
    }
    
    // ------------------------ 方法定义 ------------------------
    public synchronized int checkAccess(long accessCount, long oldTime) {
        long now = System.currentTimeMillis();
        if (countTimes.size() > accessCount) {
            if (now - oldTime <= checkTime && now >= checkTime - 60000L) {
                return 2;
            } else {
                countTimes.clear();
                countTimes.add(now);
                checkTime = now;
                return 0;
            }
        } else {
            while (countTimes.size() > 0) {
                long old = countTimes.get(0);
                if (now - old <= 60000L && now >= old - 60000L) {
                    break;
                }
                countTimes.remove(0);
            }
            countTimes.add(now);
            checkTime = now;
            return countTimes.size() > accessCount ? 1 : 0;
        }
    }
    
    public long getCheckTime() {
        return this.checkTime;
    }
    
    public String getRemoteIP() {
        return this.remoteIP;
    }
    
}
