package com.x.commons.database.pool.test;

import com.x.commons.database.pool.DB;
import com.x.commons.database.pool.Pool;
import com.x.commons.database.pool.PoolConfig;
import com.x.commons.database.pool.Pools;

import java.sql.Connection;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/7 18:03
 */
public class PoolTest {
    
    public static void main(String[] args) throws Exception {
        String url = DB.MYSQL.defaultURL("springboot");
        String user = "root";
        String pwd = "123456";
        String type = "mysql";
        String poolName = "ADPool";
        
        PoolConfig config = new PoolConfig();
        
        config.setUrl(url);
        config.setUser(user);
        config.setPassword(pwd);
        config.setType(type);
        config.setPoolName(poolName);
        config.setUseDruid(false);
        
        Pool pool = Pools.start(config);
        Connection conn = null;
        Connection connection = null;
        try {
            conn = pool.getConnection();
            String status = pool.getStatus();
            System.out.println(status);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        
    }
    
}
