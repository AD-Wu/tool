package com.x.commons.database.factory;

import com.x.commons.database.core.Database;
import com.x.commons.database.pool.Pool;
import com.x.commons.database.core.IDataReader;

/**
 * @Desc TODO
 * @Date 2019-11-29 22:46
 * @Author AD
 */
public class SqlServer extends Database {
    public SqlServer(Pool pool) {
        super(pool);
    }
    
    public int executeReader(IDataReader reader, String sql, Object[] args, int[] sqlTypes, int startIndex, int rows) throws Exception {
        String var7 = "SELECT * FROM (SELECT row_number()over(ORDER BY AX_TMP_COLUMN) AX_TMP_ROWNUM, * fromJson (SELECT top " + (startIndex + rows) + " AX_TMP_COLUMN=0, AXT1.* fromJson (" + sql + ") AXT1)) AXT2 WHERE AX_TMP_ROWNUM>=" + (startIndex + 1);
        return this.executeReader(reader, var7, args, sqlTypes);
    }
}
