package com.x.commons.database.core;

import com.x.commons.database.pool.Pool;
import com.x.commons.database.reader.IDataReader;
import com.x.commons.util.collection.Arrays;

import java.sql.*;

/**
 * @Desc 数据库基类
 * @Date 2019-11-08 19:53
 * @Author AD
 */
public abstract class Database implements IDatabase {
    
    /**
     * 数据库连接池
     */
    private final Pool pool;
    
    public Database(Pool pool) {
        this.pool = pool;
    }
    
    protected Connection getConnection() throws Exception {
        return pool.getConnection();
    }
    
    protected void fillPrepareStatement(PreparedStatement ps, Object[] args, int[] sqlTypes) throws SQLException {
        if (ps == null) return;
        if (!Arrays.isEmpty(args)) {
            
            if (!Arrays.isEmpty(sqlTypes)) {
                int min = Math.min(args.length, sqlTypes.length);
                for (int i = 0, k = 1; i < min; ++i, ++k) {
                    ps.setObject(k, args[i], sqlTypes[i]);
                }
            } else {
                for (int i = 1, L = args.length; i <= L; ++i) {
                    ps.setObject(i, args[i - 1]);
                }
            }
        }
        
    }
    
    @Override
    public int execute(String sql, Object[] args, int[] sqlTypes) throws Exception {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            fillPrepareStatement(ps, args, sqlTypes);
            return ps.executeUpdate();
        }
        
    }
    
    @Override
    public int[] executeBatch(String[] sqls) throws Exception {
        if (Arrays.isEmpty(sqls)) return new int[0];
        try (Connection conn = getConnection();
             Statement stat = conn.createStatement();) {
            for (String sql : sqls) {
                stat.addBatch(sql);
            }
            return stat.executeBatch();
        }
    }
    
    @Override
    public int executeReader(IDataReader reader, String sql, Object[] args, int[] sqlTypes) throws Exception {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
                     ResultSet.CONCUR_READ_ONLY)) {
            this.fillPrepareStatement(ps, args, sqlTypes);
            ResultSet resultSet = ps.executeQuery();
            return reader.read(resultSet);
        }
        
    }
    
    @Override
    public Object[] executeReturnGeneratedKeys(String sql, Object[] args, int[] sqlTypes, String[] rows) throws Exception {
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            
            fillPrepareStatement(ps, args, sqlTypes);
            Object[] result = null;
            if (ps.executeUpdate() > 0) {
                
                try (ResultSet rs = ps.getGeneratedKeys();) {
                    
                    if (rs.next()) {
                        
                        if (Arrays.isEmpty(rows)) {
                            int count = rs.getMetaData().getColumnCount();
                            result = new Object[count];
                            for (int i = 0; i < count; ++i) {
                                result[i] = rs.getObject(i);
                            }
                        } else {
                            result = new Object[rows.length];
                            for (int i = 0, L = rows.length; i < L; ++i) {
                                result[i] = rs.getObject(i);
                            }
                        }
                    }
                }
                conn.commit();
                
            }
            return result;
        }
    }
    
}
