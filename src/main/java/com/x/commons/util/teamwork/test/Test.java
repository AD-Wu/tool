package com.x.commons.util.teamwork.test;

import com.x.commons.util.date.DateTimes;
import com.x.commons.util.teamwork.*;

import java.util.concurrent.TimeUnit;

/**
 * @Desc TODO
 * @Date 2019-11-30 23:48
 * @Author AD
 */
public class Test {
    
    public static void main(String[] args) {
        Task<User> taskQueue = new Task<>(10);
        Task<String> respQueue = new Task<>(10);
        
        TaskScanner<User> scanner = new TaskScanner<User>(taskQueue) {
            
            int id = 1;
            
            @Override
            public User scan() throws Exception {
                User user = new User();
                user.setId(id++);
                user.setName("AD-" + id);
                user.setAge(28);
                user.setSex(true);
                user.setBirthday(DateTimes.now(false));
                return user;
            }
        };
        
        TaskActor<User, String> actor = new TaskActor<User, String>(taskQueue, respQueue) {
            
            @Override
            public String run(User task) throws Exception {
                return null;
                // if (task == null) {
                //     return "";
                // } else {
                //     int id = task.getId();
                //     if (id < 5) {
                //         TimeUnit.SECONDS.sleep(50);
                //         return "Sleep";
                //     } else {
                //         return task.toString();
                //     }
                // }
                
            }
        };
        
        ResultHandler<String> handler = new ResultHandler<String>(respQueue) {
            
            @Override
            public void handle(String s) throws Exception {
                System.out.println(s);
            }
        };
        
        Team<User, String> team = new Team<>(scanner, actor, handler);
        team.start(1, TimeUnit.SECONDS);
        
        while (true) {
            int size = taskQueue.size();
            System.out.println("task queue size = " + size);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
