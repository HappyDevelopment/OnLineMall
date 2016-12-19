package com.explore.wang.factory;

import com.explore.wang.service.UserService;
import com.explore.wang.utils.PropUtils;

/**
 * 不同的UserService实现类 ，  那么发现一个问题，  不同的实现方法要挨个写 factory 那么
 * Created by 王兆琦  on 2016/12/5 20.31.
 */
public class UserServiceFactory {


    // 只实例一个类, 静态成员变量
    private static UserServiceFactory userServiceFactory = new UserServiceFactory();
//私有化构造函数
    private UserServiceFactory(){}

    public static UserServiceFactory getFactory() {
        return userServiceFactory;
    }


    public UserService getInstance() {

        try {
            Class clazz = Class.forName(PropUtils.getValue("UserService"));
            return (UserService) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
