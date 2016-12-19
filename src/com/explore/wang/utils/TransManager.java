package com.explore.wang.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *  事务管理类
 * Created by 王兆琦  on 2016/12/15 17.06.
 */
public class TransManager {

    private static ThreadLocal<Connection> threadLocal =new ThreadLocal<Connection>(){
        protected Connection initialValue() {
            try {
                return JdbcUtils.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        };
    };


    //私有构造方法
    private TransManager(){}

    /**
     * 获取连接
     * @return 本地线程变量-- connecting
     */
    public static Connection getConnection() {
        return  threadLocal.get() ;
    }


    /**
     * 开启事务
     */
    public static void startTrans(){
        try {
            threadLocal.get().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    public static void commitTrans(){
        try {
            threadLocal.get().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTrans(){
        try {
            threadLocal.get().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     */
    public static void release(){
        try {
            //关闭资源
            threadLocal.get().close();
            // 移除此变量， 让gc进行回收  ， 不仅关闭资源，而且remove变量
            threadLocal.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
