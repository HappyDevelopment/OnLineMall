package com.explore.wang.dao;

import com.explore.wang.domain.User;

/**
 * 接口才是编程的规范
 * 注释可以只在接口里面书写， 子类实现的时候，可以查看这些注释
 * Created by 王兆琦  on 2016/12/5 19.54.
 */
public interface UserDao  extends Dao{
    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    public User findUserByUsername(String username);

    /**
     * 保存用户信息到数据库
     *
     * @param user
     */
    public void addUser(User user);

    /**
     * 根据用户名和密码查找用户
     *
     * @param username
     * @param password
     * @return
     */
    public User findUserByUsernameAndPassword(String username, String password);
}
