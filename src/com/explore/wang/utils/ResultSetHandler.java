package com.explore.wang.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 模拟dbUtils 的 ResultSetHandler
 * Created by 王兆琦  on 2016/12/8 14.18.
 */
public interface ResultSetHandler<T> {

    //因为这个接口是要接受指定的bean对象，操作结果集，返回封装好数据的bean对象的，
    //  利用类上的泛型来指定处理那种类型

    //默认方法 没写权限
    T handle(ResultSet resultSet) throws SQLException;

}
