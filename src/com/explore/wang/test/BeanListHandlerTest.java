package com.explore.wang.test;

import com.explore.wang.domain.User;
import com.explore.wang.utils.BeanListHandler;
import com.explore.wang.utils.JdbcUtils;

import java.util.List;

/**
 * Created by 王兆琦  on 2016/12/8 21.16.
 */
public class BeanListHandlerTest {

    public static void main(String[] args) {

        String sql = "select * from user";

        List<User> list = JdbcUtils.query(sql, new BeanListHandler<User>(User.class));

    }
}
