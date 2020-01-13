package com.x.commons.util.teamwork.test;

import com.x.commons.interfaces.ICallback;
import com.x.commons.util.teamwork.Boss;
import com.x.commons.util.teamwork.Task;
import com.x.commons.util.teamwork.Team;
import com.x.commons.util.teamwork.Worker;

import java.util.concurrent.TimeUnit;

/**
 * @Desc TODO
 * @Date 2019-11-30 23:48
 * @Author AD
 */
public class Test {
    
    
    public static void main(String[] args) {
        Task<User<Callback>> taskQueue = new Task<>(10);

        Boss<User<Callback>> boss = new Boss<User<Callback>>(taskQueue) {

            @Override
            public User<Callback>[] scan() throws Exception {
                User[] users = new User[10];
                for (int i = 0; i < 10; ++i) {
                    users[i] = new User(new ICallback<Callback>() {

                        @Override
                        public void succeed(Callback res) throws Exception {
                            System.out.println("callback=" + res.getId());
                        }

                        @Override
                        public void error(int status, String msg, Callback res) throws Exception {
                            System.out.println(msg);
                        }
                    });

                }
                return users;
            }

            @Override
            public boolean filter(User<Callback> user) throws Exception {
                return user.getId() > 20;
            }

        };

        Worker<User<Callback>> worker = new Worker<User<Callback>>(taskQueue) {

            @Override
            public void run(User<Callback> user) throws Exception {
                ICallback<Callback> callback = user.getCallback();
                callback.succeed(new Callback(user.getId()));
            }
        };

        Team team = new Team(boss, worker);
        team.start(1, TimeUnit.SECONDS);
        while (true) {
            int task = taskQueue.size();
            System.out.println("task queue size = " + task);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
