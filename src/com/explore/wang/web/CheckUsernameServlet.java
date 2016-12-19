package com.explore.wang.web;


import com.explore.wang.dao.UserDao;
import com.explore.wang.domain.User;
import com.explore.wang.factory.CommonFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 王兆琦  on 2016/11/29 19.12.
 */
@WebServlet(name = "CheckUsernameServlet", urlPatterns = "/CheckUsernameServlet")
public class CheckUsernameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码一致 , 不用了， 用过滤器了

        String name = request.getParameter("username");

        // 利用工厂加泛型加反射来传入接口字节码文件，来得到具体的实现类， 更加松耦合
        UserDao userDao = CommonFactory.getCommonFactory().getInstance(UserDao.class);

        User user = userDao.findUserByUsername(name);


        //此处的逻辑是返回到 ajax的 JavaScript中判断
        if (user != null) {

            //得到结果输出返回，他有接受服务器返回数据的方法 xmlHttp.responseText;
            response.getWriter().write("true");
        } else {
            response.getWriter().write("false");

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
