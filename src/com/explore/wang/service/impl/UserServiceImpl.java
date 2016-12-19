package com.explore.wang.service.impl;


import com.explore.wang.dao.UserDao;
import com.explore.wang.domain.User;
import com.explore.wang.exception.RegistException;
import com.explore.wang.factory.UserDaoFactory;
import com.explore.wang.service.UserService;

/**
 * service 业务逻辑层  需要 dao 层的支持
 * <p>
 * Created by 王兆琦  on 2016/12/3 21.55.
 */
public class UserServiceImpl implements UserService {

    // 传入 dao 层的引用 ， 其实这么做不好的，工厂，spring 先这么来把 ，
//    UserDao userDao = new UserDaoImpl();
    UserDao userDao = UserDaoFactory.getFactory().getInstance();

    /**
     * 注册用户
     *
     * @param user 由form表单封装成的bean类
     * @return
     */
    public void registUser(User user) {

        // 先进行用户名是否存在判断，
        User resultUser = userDao.findUserByUsername(user.getUsername());

        // 不为空，就是找到了
        if (resultUser != null) {

            //   在RegistServlet中捕获，处理
            throw new RegistException("用户名已存在");
        }

        // 信息无误，注册用户
        userDao.addUser(user);
    }


    /**
     *
     * @param name
     * @param password
     * @return
     */
    public User loginUser(String name, String password) {


        return userDao.findUserByUsernameAndPassword(name, password);
    }

    @Override
    public boolean hasUser(String username) {

//        User user = userDao.findUserByUsername(username);
//        if (user != null) {
//            return true;
//        }else
//            return false;


        return  userDao.findUserByUsername(username) != null;
    }
}
