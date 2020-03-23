package com.x.commons.database.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.string.Strings;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Desc
 * @Date 2020-01-07 23:04
 * @Author AD
 */
public class Pool {
    
    private DatabaseType DBType;
    
    private String poolName;
    
    private String connectionURL;
    
    // 最快连接池，比druid快
    private DataSource pool;
    
    private boolean useDruid;
    
    Pool(PoolConfig config) throws Exception {
        String type = config.getType();
        String poolName = config.getPoolName();
        String url = config.getUrl();
        String user = config.getUser();
        String driver = config.getDriver();
        String pwd = config.getPassword();
        this.DBType = DatabaseType.get(type);
        // 检查参数
        checkError(type, poolName, url, user, driver, DBType);
        driver = Strings.isNull(driver) ? DBType.driver() : driver;
        config.setDriver(driver);
        this.poolName = poolName;
        this.connectionURL = url;
        this.useDruid = config.isUseDruid();
        // 创建数据源
        if (useDruid) {
            this.pool = DruidDataSourceFactory.createDataSource(config.toProperties());
        } else {
            this.pool = new HikariDataSource(config.toHikariConfig());
        }
        if (this.DBType == DatabaseType.DERBY && url.indexOf("create=true") > 0) {
            try (Connection conn = DriverManager.getConnection(url);) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public DatabaseType getDBType() {
        return this.DBType;
    }
    
    public String getPoolName() {
        return this.poolName;
    }
    
    public String getConnectionURL() {
        return this.connectionURL;
    }
    
    void stop() throws Exception {
        if (useDruid) {
            DruidDataSource druidPool = (DruidDataSource) pool;
            druidPool.close();
        } else {
            HikariDataSource hikariPool = (HikariDataSource) pool;
            hikariPool.close();
        }
        if (this.DBType == DatabaseType.DERBY) {
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (Exception e) {
            }
        }
        
    }
    
    public Connection getConnection() throws SQLException {
        return pool.getConnection();
    }
    
    public String getStatus() {
        if (useDruid) {
            return pool.toString();
        } else {
            HikariDataSource hikariPool = (HikariDataSource) pool;
            HikariPoolMXBean info = hikariPool.getHikariPoolMXBean();
            int activeConn = info.getActiveConnections();
            int idleConn = info.getIdleConnections();
            int totalConn = info.getTotalConnections();
            int threadsAwaitingConnection = info.getThreadsAwaitingConnection();
            SB sb = New.sb();
            sb.append(hikariPool.toString()).append("[").append("\n");
            sb.append("\t").append("activeConnections:").append(activeConn).append("\n");
            sb.append("\t").append("idleConnections:").append(idleConn).append("\n");
            sb.append("\t").append("totalConnections:").append(totalConn).append("\n");
            sb.append("\t").append("threadsAwaitingConnection:").append(threadsAwaitingConnection).append("\n").append("]");
            
            return sb.toString();
        }
    }
    
    private void checkError(String type, String poolName, String url, String user,
            String driver, DatabaseType DBType) throws IllegalArgumentException {
        
        if (Strings.isNull(type)) {
            throw new IllegalArgumentException(Locals.text("commons.pool.type"));
        }
        if (Strings.isNull(poolName)) {
            throw new IllegalArgumentException(Locals.text("commons.pool.name"));
        }
        if (Strings.isNull(url)) {
            throw new IllegalArgumentException(Locals.text("commons.pool.conn"));
        }
        if (Strings.isNull(user)) {
            throw new IllegalArgumentException(Locals.text("commons.pool.user"));
        }
        if (Strings.isNull(driver) && DBType == null) {
            throw new IllegalArgumentException(Locals.text("commons.pool.type.invalid", type));
        }
    }
    
}
