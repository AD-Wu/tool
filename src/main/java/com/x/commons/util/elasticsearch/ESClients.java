package com.x.commons.util.elasticsearch;

import com.x.commons.timming.Timer;
import com.x.commons.util.bean.New;
import com.x.commons.util.date.DateTimes;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Desc TODO
 * @Date 2020-05-03 14:02
 * @Author AD
 */
public final class ESClients {
    
    private static final Map<String, ESClient> CLIENTS = New.concurrentMap();
    
    private static final Map<String, Long> TIMEOUT = New.concurrentMap();
    
    private static volatile boolean started = false;
    
    public static ESClient getClient(String ip, int port) {
        String key = ip + ":" + port;
        ESClient client = CLIENTS.get(key);
        if (client == null) {
            client = new ESClient(ip, port);
            CLIENTS.put(key, client);
            TIMEOUT.put(key, System.currentTimeMillis());
            startCheck();
        }
        return client;
    }
    
    private static synchronized void startCheck() {
        if (!started) {
            Timer.get().add(new TimeoutChecker(), 1, TimeUnit.MINUTES);
            started = true;
        }
    }
    
    private static class TimeoutChecker implements Runnable {
        
        @Override
        public void run() {
            
            Iterator<Map.Entry<String, Long>> it = ESClients.TIMEOUT.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Long> next = it.next();
                String key = next.getKey();
                long last = next.getValue();
                if (DateTimes.isMinuteAgo(last, 3)) {
                    ESClients.TIMEOUT.remove(key);
                    ESClient client = ESClients.CLIENTS.remove(key);
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
    }
    
}
