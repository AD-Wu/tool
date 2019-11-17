package com.x.commons.database.reader;

import java.sql.ResultSet;

/**
 * @Desc TODO
 * @Date 2019-11-08 17:29
 * @Author AD
 */
public interface IDataReader {
    int read(ResultSet rs) throws Exception;
}
