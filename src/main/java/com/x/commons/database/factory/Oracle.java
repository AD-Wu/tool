package com.x.commons.database.factory;

import com.x.commons.database.core.Database;
import com.x.commons.database.pool.Pool;
import com.x.commons.database.core.IDataReader;

/**
 * @Desc TODO
 * @Date 2019-11-29 22:46
 * @Author AD
 */
public class Oracle extends Database {
    
    public Oracle(Pool pool) {
        super(pool);
    }
    
    public int executeReader(IDataReader reader, String table, Object[] args, int[] sqlTypes, int startIndex, int rows) throws Exception {
        String sql = "SELECT * FROM (SELECT AXT1.*, ROWNUM AX_ROWNUM FROM (" + table + ") AXT1 WHERE ROWNUM<=" + (startIndex + rows) +
                      ") AXT2 WHERE AX_ROWNUM>=" + (startIndex + 1);
        return this.executeReader(reader, sql, args, sqlTypes);
    }
    
}
