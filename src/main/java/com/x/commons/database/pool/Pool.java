package com.x.commons.database.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.string.Strings;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Desc
 * @Date 2020-01-07 23:04
 * @Author AD
 */
public class Pool {
    
    private DatabaseType type;
    
    private String poolName;
    
    private String connectionURL;
    
    // private DruidDataSource pool;
    // 最快连接池，比druid快
    private DataSource pool;
    
    private boolean isUseDruid;
    
    Pool(PoolConfig config) throws Exception {
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
                            switch (type) {
                                case "MYSQL":
                                    this.type = DatabaseType.MYSQL;
                                    driver = DB.MYSQL.driver();
                                    break;
                                case "ORACLE":
                                    this.type = DatabaseType.ORACLE;
                                    driver = DB.ORACLE.driver();
                                    break;
                                case "SQLSERVER":
                                    this.type = DatabaseType.SQLSERVER;
                                    driver = DB.SQLSERVER.driver();
                                    break;
                                case "DERBY":
                                    this.type = DatabaseType.DERBY;
                                    driver = DB.DERBY.driver();
                                    
                                    break;
                                case "OTHERS":
                                    this.type = DatabaseType.OTHERS;
                                    driver = DB.OTHERS.driver();
                                    break;
                                default:
                                    throw new IllegalArgumentException(
                                            Locals.text("commons.pool.type.invalid", type));
                            }
                            config.setDriver(driver);
                        } else {
                            driver = config.getDriver();
                        }
                        this.type = DatabaseType.MYSQL;
                        this.poolName = poolName;
                        this.connectionURL = url;
                        this.isUseDruid = config.isUseDruid();
                        // 创建数据源
                        if (isUseDruid) {
                            Properties prop = config.toProperties();
                            this.pool = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
                        } else {
                            HikariConfig hikariConfig = config.toHikariConfig();
                            this.pool = new HikariDataSource(hikariConfig);
                        }
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
    
    public String getConnectionURL() {
        return this.connectionURL;
    }
    
    void stop() throws Exception {
        if (isUseDruid) {
            DruidDataSource druidPool = (DruidDataSource) pool;
            druidPool.close();
        } else {
            HikariDataSource hikariPool = (HikariDataSource) pool;
            hikariPool.close();
        }
        if (this.type == DatabaseType.DERBY) {
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
        if(isUseDruid){
            return pool.toString();
        }else{
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
    
}
