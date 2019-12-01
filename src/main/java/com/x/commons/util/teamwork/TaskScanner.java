package com.x.commons.util.teamwork;

/**
 * @Desc
 * @Date 2019-11-30 19:38
 * @Author AD
 */
public abstract class TaskScanner<REQ> implements Runnable {
    
    private final Task<REQ> reqs;
    
    public TaskScanner(Task<REQ> reqs) {
        this.reqs = reqs;
    }
    
    @Override
    public void run() {
        try {
            REQ req = scan();
            if (req != null) {
                reqs.put(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public abstract REQ scan() throws Exception;
    
}
