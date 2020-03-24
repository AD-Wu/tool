package com.x.commons.database;

import com.x.commons.database.core.IDatabase;
import com.x.commons.database.factory.*;
import com.x.commons.database.pool.DatabaseType;
import com.x.commons.database.pool.Pool;
import com.x.commons.database.pool.Pools;
import com.x.commons.database.core.IDataReader;
import com.x.commons.local.Locals;

/**
 * @Desc
 * @Date 2019-11-08 17:26
 * @Author AD
 */
public class DatabaseAccess implements IDatabase {
    private final IDatabase database;
    
    public DatabaseAccess(String poolName) throws Exception {
        Pool pool = Pools.getPool(poolName);
        if (pool == null) {
            throw new Exception(Locals.text("commons.pool.name.invalid", poolName));
        } else {
            DatabaseType type = pool.getDBType();
            switch(type) {
                case MYSQL:
                    database = new MySQL(pool);
                    break;
                case DERBY:
                    database = new Derby(pool);
                    break;
                case ORACLE:
                    database = new Oracle(pool);
                    break;
                case SQLSERVER:
                    database = new SqlServer(pool);
                    break;
                default:
                    database = new Others(pool);
            }
            
        }
    }
    
    @Override
    public int execute(String sql, Object[] args, int[] sqlTypes) throws Exception {
        return database.execute(sql, args, sqlTypes);
    }
    
    @Override
    public int[] executeBatch(String[] sqls) throws Exception {
        return database.executeBatch(sqls);
    }
    
    @Override
    public int executeReader(IDataReader reader, String sql, Object[] args, int[] sqlTypes) throws Exception {
        return database.executeReader(reader, sql, args, sqlTypes);
    }
    
    @Override
    public int executeReader(IDataReader reader, String sql, Object[] args, int[] sqlTypes, int startIndex, int rows) throws Exception {
        return this.database.executeReader(reader, sql, args, sqlTypes, startIndex, rows);
    }
    
    @Override
    public Object[] executeReturnGeneratedKeys(String sql, Object[] args, int[] sqlTypes, String[] var4) throws Exception {
        return database.executeReturnGeneratedKeys(sql, args, sqlTypes, var4);
    }
}
