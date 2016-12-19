package com.explore.wang.web;


import com.explore.wang.domain.User;
import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.UserService;
import com.explore.wang.utils.MD5Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 用户登录处理的servlet
 * 假如勾选了记住用户，那么保存在cookie中，下次进入登录界面，默认用户名栏为之前输入的用户名
 * 没有勾选用户名，下次进入是全新界面
 * Created by 王兆琦  on 2016/11/30 11.36.
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 处理请求参数乱码 ， 不用了，使用过滤器

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remName = request.getParameter("remname");  // 选择为true ，没有为null

        UserService userService = CommonFactory.getCommonFactory().getInstance(UserService.class);


        // 假如用户勾选记住用户名
        if ("true".equals(remName)) {

            //进行保存cookie
            // 此处出现一个问题，就是在保存 Username值得时候，乱码问题，
            // 发送字节流是使用的iso-8859-1
            Cookie cookie = new Cookie("remName", URLEncoder.encode(username, "utf-8"));
            //设置path 和保存时间
            cookie.setPath(request.getContextPath() + "/");    // 可能路径是空，加上一个空格来无论如何都是整个web应用
            cookie.setMaxAge(3600 * 24 * 7);

            // 服务器响应添加一个cookie
            response.addCookie(cookie);

        } else {
            //没有勾选，为null ， 删除之前存在的cookie
            Cookie cookie = new Cookie("remName", "");

            //设置path ， domain和之前一致  保存时间为0
            cookie.setPath(request.getContextPath() + "/");
            cookie.setMaxAge(0);

            // 服务器响应添加一个cookie
            response.addCookie(cookie);

        }

        password = MD5Utils.md5(password);
        User user = userService.loginUser(username, password);

        if (user != null) {
            //保存到Session域中
            request.getSession().setAttribute("user", user);

            // 此处如果yoghurt勾选了30天自动登录，进行Cookie的保存
            if ("true".equals(request.getParameter("autologin"))) {
                //保存cookie
                Cookie cookie = new Cookie("autologin", URLEncoder.encode(username + ":" + password, "utf-8"));
                cookie.setPath(request.getContextPath() + "/");
                cookie.setMaxAge(3600 * 24 * 30);

                response.addCookie(cookie);
            }else {
                //不进行保存cookie
            }

            //登录成功，跳转到首页
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        } else {
            //保存错误消息
            request.setAttribute("msg", "用户名或密码错误");
            //转发到登录界面
            request.getRequestDispatcher(request.getContextPath() + "/login.jsp").forward(request, response);
        }

    }
}
