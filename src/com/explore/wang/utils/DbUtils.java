package com.explore.wang.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * 利用 DBUtils来进行数据库层访问的解放代码
 * Created by 王兆琦  on 2016/12/5 21.12.
 */
public class DbUtils {


    private static DataSource pool = new ComboPooledDataSource();

    private DbUtils(){}

    /**
     *  得到数据源
     * @return  返回c3p0连接池的数据源
     */
    public static DataSource getPool(){

        return pool;
    }

    public static Connection getConnection() throws SQLException {

        return pool.getConnection();
    }
}
