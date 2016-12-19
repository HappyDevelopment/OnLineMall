package com.explore.wang.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 王兆琦  on 2016/11/30 18.47.
 */
@WebServlet(name = "LogoutServlet",urlPatterns = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置页面编码，可以配置 过滤器

        //现在是注销用户操作， 清除 登录成功的session

        //首先判断session有没有 ， 先的是健壮性判断 等环境问题
        if (request.getSession(false) != null) {

            if (request.getSession().getAttribute("user") != null) {

                //杀死 session ， 可以有多种， 现在使用 自杀
                request.getSession().invalidate();

                // 清除 autologincookie
                Cookie cookie = new Cookie("autologin", "");
                cookie.setPath(request.getContextPath() + "/");
                cookie.setMaxAge(0);

                response.addCookie(cookie);
            }
        }

        //跳转后首页
        response.sendRedirect(request.getContextPath() + "/index.jsp");

    }
}
