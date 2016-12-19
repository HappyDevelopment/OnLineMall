package com.explore.wang.dao.impl;


import com.explore.wang.dao.UserDao;
import com.explore.wang.domain.User;
import com.explore.wang.utils.DbUtils;
import com.explore.wang.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;

/**
 * 用户访问数据库
 * <p>
 * 项目的改造， 利用DBUtils 来进行数据库的访问 , 他会进行数据库的关闭操作
 * Created by 王兆琦  on 2016/12/3 19.04.
 */
public class UserDaoImpl implements UserDao {

    public void addUser(User user) {
        try {
            QueryRunner queryRunner = new QueryRunner(DbUtils.getPool());
            // QueryRunner 来简易实现添加用户操作。
            JdbcUtils.update("insert into user values(null,?,?,?,?,'user')",
                    user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail());

            // 他会进行数据库的关闭操作
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    public User findUserByUsername(String name) {
        try {
            QueryRunner queryRunner = new QueryRunner(DbUtils.getPool());

            // 利用DBUtils的 ResultSetHandler 的Bean操作类来简易实现数据的封装为类
            User user = JdbcUtils.query("select * from user where username = ?",
                    new com.explore.wang.utils.BeanHandler<User>(User.class), name);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public User findUserByUsernameAndPassword(String name, String password) {
        try {
            QueryRunner queryRunner = new QueryRunner(DbUtils.getPool());

            User user = JdbcUtils.query("select * from user where username = ? and password = ?", new com.explore.wang.utils.BeanHandler<User>(User.class), name,password);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
