package com.x.commons.database.pool;

import com.x.commons.util.string.Strings;

/**
 * @Desc TODO
 * @Date 2019-11-16 19:07
 * @Author AD
 */
public enum DB {

    MYSQL {
        @Override
        public String defaultURL(String database) {
            return url("localhost", 3306, database);
        }

        @Override
        public String url(String ip, int port, String database) {
            return Strings.replace("jdbc:mysql://{0}:{1}/{2}", ip, port, database);
        }

        @Override
        public String driver() {
            return "com.mysql.jdbc.Driver";
        }
    },
    ORACLE {
        @Override
        public String defaultURL(String database) {
            return url("localhost", 1521, "orcl");
        }

        @Override
        public String url(String ip, int port, String database) {
            // jdbc:oracle:thin:@localhost:1521:orcl
            return Strings.replace("jdbc:oracle:thin:@{0}:{1}:{2}", ip, port, database);
        }

        @Override
        public String driver() {
            return "oracle.jdbc.driver.OracleDriver";
        }
    };

    public enum Type {
        MYSQL,
        ORACLE,
        DERBY,
        SQL_SERVER,
        OTHERS;
    }

    /**
     * 获取默认的数据库url。如：MySQL -> jdbc:mysql://localhost:3306/database
     *
     * @param database 数据库名或
     * @return
     */
    public abstract String defaultURL(String database);

    /**
     * 获取数据库url
     *
     * @param ip       数据库所在ip
     * @param port     数据库端口
     * @param database 数据库
     * @return
     */
    public abstract String url(String ip, int port, String database);

    /**
     * 获取数据库驱动类名
     *
     * @return
     */
    public abstract String driver();
}
