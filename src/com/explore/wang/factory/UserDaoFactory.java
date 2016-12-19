package com.explore.wang.factory;

import com.explore.wang.dao.UserDao;
import com.explore.wang.utils.PropUtils;

/**
 * 单例模式的 提供不同实现数据库方法的 UserDao工厂
 * <p>
 * Created by 王兆琦  on 2016/12/5 19.57.
 */
public class UserDaoFactory {

    // 只创建一个实例
    private static UserDaoFactory userDaoFactory = new UserDaoFactory();

    /**
     * 私有化构造函数
     */
    private UserDaoFactory() {
    }

    /**
     * 提供外界的获取方法
     * @return
     */
    public static UserDaoFactory getFactory(){
        return userDaoFactory;
    }
    /**
     * 通过反射获取
     * @return UserDao
     */
    public UserDao getInstance() {

        //获取 UserDao 的 字节码文件
        try {
            Class clazz = Class.forName(PropUtils.getValue("UserDao"));

            //返回类的实例
            return (UserDao) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
