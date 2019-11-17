package com.x.commons.database.factory;

import com.x.commons.database.core.Database;
import com.x.commons.database.pool.Pool;
import com.x.commons.database.reader.IDataReader;

/**
 * @Desc TODO
 * @Date 2019-11-08 22:16
 * @Author AD
 */
public class MySQL extends Database {

    public MySQL(Pool var1) {
        super(var1);
    }

    public int executeReader(IDataReader reader,String table,Object[] args,int[] sqlTypes,int start,int rows) throws Exception {
        String sql = "SELECT * FROM (" + table + ") AXT1 LIMIT " + start + ", " + rows;
        return this.executeReader(reader,sql,args,sqlTypes);
    }

}