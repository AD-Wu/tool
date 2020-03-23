package com.x.commons.database;

import com.x.commons.database.core.IDatabase;
import com.x.commons.database.factory.*;
import com.x.commons.database.pool.DatabaseType;
import com.x.commons.database.pool.Pool;
import com.x.commons.database.pool.Pools;
import com.x.commons.database.reader.IDataReader;
import com.x.commons.local.Locals;

/**
 * @Desc
 * @Date 2019-11-08 17:26
 * @Author AD
 */
public class DatabaseAccess implements IDatabase {
    private IDatabase access;
    
    public DatabaseAccess(String poolName) throws Exception {
        Pool pool = Pools.getPool(poolName);
        if (pool == null) {
            throw new Exception(Locals.text("commons.pool.name.invalid", poolName));
        } else {
            DatabaseType type = pool.getDBType();
            switch(type) {
                case MYSQL:
                    access = new MySQL(pool);
                    break;
                case DERBY:
                    access = new Derby(pool);
                    break;
                case ORACLE:
                    access = new Oracle(pool);
                    break;
                case SQLSERVER:
                    access = new SqlServer(pool);
                    break;
                default:
                    access = new Others(pool);
            }
            
        }
    }
    
    @Override
    public int execute(String sql, Object[] args, int[] sqlTypes) throws Exception {
        return access.execute(sql, args, sqlTypes);
    }
    
    @Override
    public int[] executeBatch(String[] sqls) throws Exception {
        return access.executeBatch(sqls);
    }
    
    @Override
    public int executeReader(IDataReader reader, String sql, Object[] args, int[] sqlTypes) throws Exception {
        return access.executeReader(reader, sql, args, sqlTypes);
    }
    
    @Override
    public int executeReader(IDataReader reader, String sql, Object[] args, int[] sqlTypes, int startIndex, int rows) throws Exception {
        return this.access.executeReader(reader, sql, args, sqlTypes, startIndex, rows);
    }
    
    @Override
    public Object[] executeReturnGeneratedKeys(String sql, Object[] args, int[] sqlTypes, String[] var4) throws Exception {
        return access.executeReturnGeneratedKeys(sql, args, sqlTypes, var4);
    }
}
