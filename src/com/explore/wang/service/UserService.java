package com.explore.wang.service;

import com.explore.wang.domain.User;

/**
 * Created by 王兆琦  on 2016/12/5 20.26.
 */
public interface UserService extends Service{

    /**
     * 注册用户
     * @param user
     */
    public void registUser(User user);

    /**
     * 登陆用户
     * @param username
     * @param password
     * @return
     */
    public User loginUser(String username, String password);

    /**
     * 根据用户名检查用户是否存在
     * @param username
     * @return
     */
    public boolean hasUser(String username);
}
