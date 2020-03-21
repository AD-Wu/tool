package com.x.commons.util.teamwork;

import com.x.commons.util.string.Strings;

import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Date 2020-03-21 14:02
 * @Author AD
 */
public class Test {
    
    public static void main(String[] args) {
        Test test = new Test();
        test.test();
    }
    
    public void test() {
        Team<XTask> team = new Team<XTask>(1) {
            
            @Override
            protected XTask[] getTask() {
                XTask[] tasks = new XTask[100];
                for (int i = 0; i < 100; ++i) {
                    tasks[i] = new XTask(i, new Test.ICallback<Integer>() {
                        
                        @Override
                        public void onCallback(Integer bean) {
                            System.out.println("callback->" + bean);
                        }
                    });
                }
                return tasks;
            }
            
            @Override
            protected void runTask(XTask task) {
                System.out.println("work->" + task.getId());
                ICallback callback = task.getCallback();
                callback.onCallback(task.getId());
            }
            
        };
        team.start(1, TimeUnit.SECONDS);
    }
    
    private class XTask<T> {
        
        private final int id;
        
        private final ICallback<T> callback;
        
        private XTask(int id, ICallback<T> callback) {
            this.id = id;
            this.callback = callback;
        }
        
        public int getId() {
            return id;
        }
        
        public ICallback<T> getCallback() {
            return callback;
        }
        
        @Override
        public String toString() {
            return Strings.simpleToString(this);
        }
        
    }
    
    private interface ICallback<T> {
        
        void onCallback(T bean);
        
    }
    
}
