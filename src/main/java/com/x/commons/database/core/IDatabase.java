package com.x.commons.database.core;

import com.x.commons.database.reader.IDataReader;

/**
 * @Desc
 * @Date 2019-11-08 17:29
 * @Author AD
 */
public interface IDatabase {

    int execute(String sql,Object[] args,int[] sqlTypes) throws Exception;

    int[] executeBatch(String[] sqls) throws Exception;

    /**
     * @param reader   读取器
     * @param sql      SQL语句
     * @param args     参数
     * @param sqlTypes 字段类型，详见java.sql.Types
     * @return
     * @throws Exception
     */
    int executeReader(IDataReader reader,String sql,Object[] args,int[] sqlTypes) throws Exception;

    int executeReader(IDataReader reader,String table,Object[] args,int[] sqlTypes,int start,int rows) throws Exception;

    Object[] executeReturnGeneratedKeys(String sql,Object[] args,int[] sqlTypes,String[] rows) throws Exception;

}
