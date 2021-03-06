package com.x.commons.database.factory;

import com.x.commons.database.core.Database;
import com.x.commons.database.pool.Pool;
import com.x.commons.database.core.IDataReader;

/**
 * @Desc
 * @Date 2019-11-08 22:16
 * @Author AD
 */
public class MySQL extends Database {

    public MySQL(Pool pool) {
        super(pool);
    }

    public int executeReader(IDataReader reader,String table,Object[] args,int[] sqlTypes,int startIndex,int rows) throws Exception {
        String sql = "SELECT * FROM (" + table + ") AXT1 LIMIT " + startIndex + ", " + rows;
        return this.executeReader(reader,sql,args,sqlTypes);
    }

}