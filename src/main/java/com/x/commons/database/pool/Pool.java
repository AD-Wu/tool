package com.x.commons.database.pool;

import com.ax.commons.local.LocalManager;
import com.x.commons.local.Locals;
import com.x.commons.util.string.Strings;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.management.ObjectName;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Desc 数据库连接池
 * @Date 2019-11-08 17:31
 * @Author AD
 */
public class Pool {
    
    private static final String POOL_DRIVER_URL = "jdbc:apache:commons:dbcp:";
    
    private DB.Type type;
    
    private String poolName;
    
    private String connectionString;
    
    private String poolString;
    
    Pool(PoolConfig config) throws Exception {
        String type = config.getType(); String poolName = config.getPoolName(); String driver = config.getDriver();
        String url = config.getUrl(); String user = config.getUser(); String pwd = config.getPwd();
        if (!Strings.isNull(type)) {
            if (!Strings.isNull(poolName)) {
                if (!Strings.isNull(url)) {
                    if (!Strings.isNull(user)) {
                        type = type.toUpperCase(); if (Strings.isNull(driver)) {
                            if ("DERBY".equals(type)) {
                                this.type = DB.Type.DERBY; driver = DB.DERBY.driver();
                            } else if ("MYSQL".equals(type)) {
                                this.type = DB.Type.MYSQL; driver = DB.MYSQL.driver();
                            } else if ("ORACLE".equals(type)) {
                                this.type = DB.Type.ORACLE; driver = DB.ORACLE.driver();
                            } else if ("SQLSERVER".equals(type)) {
                                this.type = DB.Type.SQLSERVER; driver = DB.SQLSERVER.driver();
                            } else {
                                if (!"OTHERS".equals(type)) {
                                    throw new IllegalArgumentException(Locals.text("commons.pool.type.invalid", type));
                                } this.type = DB.Type.OTHERS; driver = DB.OTHERS.driver();
                            }
                        }
                        
                        this.poolName = poolName; this.connectionString = url;
                        this.poolString = "jdbc:apache:commons:dbcp2:" + poolName; Class.forName(driver).newInstance();
                        
                        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
                        poolConfig.setMaxTotal(config.getMaxActive()); poolConfig.setMinIdle(config.getMinIdle());
                        poolConfig.setMaxIdle(config.getMaxIdle()); poolConfig.setMaxWaitMillis(config.getMaxWait());
                        poolConfig.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
                        poolConfig.setTestOnBorrow(config.isTestOnBorrow());
                        poolConfig.setTestOnReturn(config.isTestOnReturn());
                        poolConfig.setTestWhileIdle(config.isTestWhileIdle());
                        poolConfig.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
                        poolConfig.setBlockWhenExhausted(config.isExhaustedAction());
                        
                        // 创建泛型对象池
                        GenericObjectPool<Connection> pool = new GenericObjectPool(null, poolConfig);
                        // 创建连接工厂
                        DriverManagerConnectionFactory connFactory = new DriverManagerConnectionFactory(url, user, pwd);
                        new PoolableConnectionFactory(connFactory, new ObjectName(poolName));
                        
                        // 获取驱动
                        Class.forName("org.apache.commons.dbcp2.PoolingDriver");
                        PoolingDriver poolingDriver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp2:");
                        // 注册连接池
                        poolingDriver.registerPool(poolName, pool);
                        if (this.type == DB.Type.DERBY && url.indexOf("create=true") > 0) {
                            Connection conn = null;
                            
                            try {
                                conn = DriverManager.getConnection(url);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (conn != null) {
                                    try {
                                        conn.close();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } conn = null;
                                }
                                
                            }
                        }
                        
                    } else {
                        throw new IllegalArgumentException(Locals.text("commons.pool.user"));
                    }
                } else {
                    throw new IllegalArgumentException(LocalManager.defaultText("commons.pool.conn"));
                }
            } else {
                throw new IllegalArgumentException(LocalManager.defaultText("commons.pool.name"));
            }
        } else {
            throw new IllegalArgumentException(LocalManager.defaultText("commons.pool.type"));
        }
        
    }
    
    public DB.Type getType() {
        return this.type;
    }
    
    public String getPoolName() {
        return this.poolName;
    }
    
    public String getConnectionString() {
        return this.connectionString;
    }
    
    void stop() throws Exception {
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp2:");
        driver.closePool(this.poolName); if (this.type == DB.Type.DERBY) {
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (Exception e) {
            }
        }
        
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(poolString);
    }
    
    public String getStatus() throws SQLException {
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp2:");
        ObjectPool pool = driver.getConnectionPool(poolName);
        return Locals.text("commons.pool.status", pool.getNumActive(), pool.getNumIdle());
    }
    
}
