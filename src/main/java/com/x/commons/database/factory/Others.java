package com.x.commons.database.factory;

import com.x.commons.database.core.Database;
import com.x.commons.database.pool.Pool;
import com.x.commons.database.reader.IDataReader;

import java.sql.ResultSet;

/**
 * @Desc TODO
 * @Date 2019-11-29 22:46
 * @Author AD
 */
public class Others extends Database {
    
    public Others(Pool pool) {
        super(pool);
    }
    
    public int executeReader(IDataReader reader, String sql, Object[] args, int[] sqlTypes, int startIndex, int rows) throws Exception {
        return executeReader(new Others.OtherReader(reader, startIndex), sql, args, sqlTypes);
    }
    
    private class OtherReader implements IDataReader {
        
        private IDataReader reader;
        
        private int startIndex;
        
        OtherReader(IDataReader reader, int startIndex) {
            this.reader = reader;
            this.startIndex = startIndex;
        }
        
        public int read(ResultSet resultSet) throws Exception {
            resultSet.absolute(this.startIndex + 1);
            return reader.read(resultSet);
        }
        
    }
    
}
