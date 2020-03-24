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
 * @Desc 数据库连接池对象
 * @Date 2020-01-07 23:04
 * @Author AD
 */
public final class Pool {

    // 数据库类型
    private final DatabaseType DBType;

    // 连接池名字（使用框架时是协议名）
    private final String poolName;

    // jdbcURL
    private final String url;

    // 数据库池
    private final DataSource pool;

    // 使用druid（默认使用Hikari,最快）
    private final boolean useDruid;

    Pool(PoolConfig config) throws Exception {
        String type = config.getType();
        this.DBType = DatabaseType.get(type);
        this.poolName = config.getPoolName();
        this.url = config.getUrl();
        this.useDruid = config.isUseDruid();
        String user = config.getUser();
        String driver = config.getDriver();
        String pwd = config.getPassword();

        // 检查参数
        checkError(type, poolName, url, user, driver, DBType);
        driver = Strings.isNull(driver) ? DBType.driver() : driver;
        config.setDriver(driver);

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

    public String getUrl() {
        return this.url;
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
            sb.append("\t").append("threadsAwaitingConnection:").append(
                    threadsAwaitingConnection).append("\n").append("]");

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
