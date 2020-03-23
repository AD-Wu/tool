package com.x.commons.database.pool;

/**
 * @Desc 关系型数据库类型枚举
 * @Date 2019-12-05 22:07
 * @Author AD
 */
public enum DatabaseType {
    
    MYSQL("mysql") {
        @Override
        public String driver() {
            return "com.mysql.cj.jdbc.Driver";
        }
        
        @Override
        public String localURL() {
            return "jdbc:mysql://localhost:3306/database?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior" +
                   "=convertToNull";
        }
    },
    ORACLE("oracle") {
        @Override
        public String driver() {
            return "oracle.jdbc.driver.OracleDriver";
        }
        
        @Override
        public String localURL() {
            return "jdbc:oracle:thin:@localhost:1521:orcl";
        }
    },
    DERBY("derby") {
        @Override
        public String driver() {
            return "org.apache.derby.jdbc.EmbeddedDriver";
        }
        
        @Override
        public String localURL() {
            return "";
        }
    },
    SQLSERVER("sqlserver") {
        @Override
        public String driver() {
            return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        }
        
        @Override
        public String localURL() {
            return "";
        }
    },
    OTHERS("other") {
        @Override
        public String driver() {
            return "sun.jdbc.odbc.JdbcOdbcDriver";
        }
        
        @Override
        public String localURL() {
            return "";
        }
    };
    
    private final String type;
    
    DatabaseType(String type) {
        this.type = type;
    }
    
    public static DatabaseType get(String type) {
        for (DatabaseType db : values()) {
            if (db.type.equalsIgnoreCase(type)) {
                return db;
            }
        }
        return null;
    }
    
    public String type() {
        return type;
    }
    
    public abstract String driver();
    
    public abstract String localURL();
}
