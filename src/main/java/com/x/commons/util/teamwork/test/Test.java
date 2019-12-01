package com.x.commons.util.teamwork.test;

import com.x.commons.interfaces.ICallback;
import com.x.commons.util.teamwork.*;

import java.util.concurrent.TimeUnit;

/**
 * @Desc TODO
 * @Date 2019-11-30 23:48
 * @Author AD
 */
public class Test {
    
    public static void main(String[] args) {
        Task<User<String>> taskQueue = new Task<>(10);
        Task<User<String>> respQueue = new Task<>(10);
        
        ReqScanner<User<String>> scanner = new ReqScanner<User<String>>(taskQueue) {
            
            @Override
            public User<String>[] scan() throws Exception {
                User[] users = new User[10];
                for (int i = 0; i < 10; ++i) {
                    users[i] = new User(new ICallback<String>() {
                        
                        @Override
                        public void succeed(String res) throws Exception {
                            System.out.println("succeed=" + res);
                        }
                        
                        @Override
                        public void error(int status, String msg, String res) throws Exception {
                            System.out.println("error=" + res);
                        }
                    });
                }
                return users;
            }
            
            @Override
            public boolean filter(User<String> user) throws Exception {
                return user.getId() > 20;
            }
            
        };
        
        ReqActor<User<String>, User<String>> actor = new ReqActor<User<String>, User<String>>(taskQueue, respQueue) {
            
            @Override
            public User<String> run(User<String> user) throws Exception {
                user.setName("callback=" + user.getId());
                return user;
            }
        };
        
        RespHandler<User<String>> handler = new RespHandler<User<String>>(respQueue) {
            
            @Override
            public void handle(User<String> user) throws Exception {
                ICallback<String> callback = user.getCallback();
                callback.succeed("结果-" + user.getId());
            }
        };
        
        Team<User<String>, User<String>> team = new Team<>(scanner, actor, handler);
        team.start(1, TimeUnit.SECONDS);
        while (true) {
            int task = taskQueue.size();
            int resp = respQueue.size();
            System.out.println("task queue size = " + task);
            System.out.println("resp queue size = " + resp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
