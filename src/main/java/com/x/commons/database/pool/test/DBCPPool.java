package com.x.commons.database.pool.test;

import com.x.commons.database.pool.DB;
import com.x.commons.database.pool.DatabaseType;
import com.x.commons.database.pool.PoolConfig;
import com.x.commons.local.Locals;
import com.x.commons.util.string.Strings;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Desc 数据库连接池
 * @Date 2019-11-08 17:31
 * @Author AD
 */
public class DBCPPool {
    
    private static final String POOL_DRIVER_URL = "jdbc:apache:commons:dbcp:";
    
    private DatabaseType type;
    
    private String poolName;
    
    private String connectionString;
    
    private String poolString;
    
    DBCPPool(PoolConfig config) throws Exception {
        String type = config.getType();
        String poolName = config.getPoolName();
        String driver = config.getDriver();
        String url = config.getUrl();
        String user = config.getUser();
        String pwd = config.getPassword();
        if (!Strings.isNull(type)) {
            if (!Strings.isNull(poolName)) {
                if (!Strings.isNull(url)) {
                    if (!Strings.isNull(user)) {
                        type = type.toUpperCase();
                        if (Strings.isNull(driver)) {
                            if ("DERBY".equals(type)) {
                                this.type = DatabaseType.DERBY;
                                driver = DB.DERBY.driver();
                            } else if ("MYSQL".equals(type)) {
                                this.type = DatabaseType.MYSQL;
                                driver = DB.MYSQL.driver();
                            } else if ("ORACLE".equals(type)) {
                                this.type = DatabaseType.ORACLE;
                                driver = DB.ORACLE.driver();
                            } else if ("SQLSERVER".equals(type)) {
                                this.type = DatabaseType.SQLSERVER;
                                driver = DB.SQLSERVER.driver();
                            } else {
                                if (!"OTHERS".equals(type)) {
                                    throw new IllegalArgumentException(
                                            Locals.text("commons.pool.type.invalid", type));
                                }
                                this.type = DatabaseType.OTHERS;
                                driver = DB.OTHERS.driver();
                            }
                        } else {
                            driver = config.getDriver();
                        }
                        
                        this.poolName = poolName;
                        this.connectionString = url;
                        this.poolString = POOL_DRIVER_URL + poolName;
                        Class.forName(driver).newInstance();
                        
                        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
                        poolConfig.setMaxTotal(config.getMaxActive());
                        poolConfig.setMinIdle(config.getMinIdle());
                        // poolConfig.setMaxIdle(config.getMaxIdle());
                        poolConfig.setMaxWaitMillis(config.getMaxWait());
                        poolConfig.setMinEvictableIdleTimeMillis(
                                config.getMinEvictableIdleTimeMillis());
                        poolConfig.setTestOnBorrow(config.isTestOnBorrow());
                        poolConfig.setTestOnReturn(config.isTestOnReturn());
                        poolConfig.setTestWhileIdle(config.isTestWhileIdle());
                        poolConfig.setTimeBetweenEvictionRunsMillis(
                                config.getTimeBetweenEvictionRunsMillis());
                        poolConfig.setBlockWhenExhausted(config.isExhaustedAction());
                        // ObjectName name = new ObjectName(url);
                        
                        // 连接工厂
                        DriverManagerConnectionFactory connFactory = new DriverManagerConnectionFactory(
                                url, user, pwd);
                        // 连接池工厂
                        PoolableConnectionFactory factory = new PoolableConnectionFactory(connFactory, null);
                        // 连接池对象
                        GenericObjectPool<Connection> pool = new GenericObjectPool(factory, poolConfig);
                        
                        // 注册连接池驱动
                        Class.forName("org.apache.commons.dbcp2.PoolingDriver");
                        PoolingDriver poolingDriver = (PoolingDriver) DriverManager.getDriver(POOL_DRIVER_URL);
                        // 注册连接池
                        poolingDriver.registerPool(poolName, pool);
                        if (this.type == DatabaseType.DERBY && url.indexOf("create=true") > 0) {
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
                                    }
                                    conn = null;
                                }
                                
                            }
                        }
                        
                    } else {
                        throw new IllegalArgumentException(Locals.text("commons.pool.user"));
                    }
                } else {
                    throw new IllegalArgumentException(
                            Locals.text("commons.pool.conn"));
                }
            } else {
                throw new IllegalArgumentException(Locals.text("commons.pool.name"));
            }
        } else {
            throw new IllegalArgumentException(Locals.text("commons.pool.type"));
        }
        
    }
    
    public DatabaseType getType() {
        return this.type;
    }
    
    public String getPoolName() {
        return this.poolName;
    }
    
    public String getConnectionString() {
        return this.connectionString;
    }
    
    void stop() throws Exception {
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver(POOL_DRIVER_URL);
        driver.closePool(this.poolName);
        if (this.type == DatabaseType.DERBY) {
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
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver(POOL_DRIVER_URL);
        ObjectPool pool = driver.getConnectionPool(poolName);
        return Locals.text("commons.pool.status", pool.getNumActive(), pool.getNumIdle());
    }
    
}
