package com.explore.wang.filter;

import com.explore.wang.domain.User;
import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * 自动登录的过滤器
 * Created by 王兆琦  on 2016/12/7 18.00.
 */
@WebFilter(filterName = "AutoLoginFilter", urlPatterns = "/*")
public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        // 自动登录的条件
        // 用户未登录 ！！
        // 有cookie， 用户名密码准确，

        HttpServletRequest request = (HttpServletRequest) req;
        Cookie autoLogin = null;

        //用户未登录  ，  没有session 或者是 session没有user
        if (request.getSession(false) == null || request.getSession().getAttribute("user") == null) {

            //2.带着自动登录Cookie
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {

                for (Cookie c : cookies) {
                    if ("autologin".equals(c.getName())) {
                        autoLogin = c;

                        String value = URLDecoder.decode(autoLogin.getValue(),"utf-8");

                        String name = value.split(":")[0];
                        String password = value.split(":")[1];

                        UserService userService = CommonFactory.getCommonFactory().getInstance(UserService.class);

                        User user = userService.loginUser(name, password);

                        if (user != null) {
                            //信息准确
                            //保存到Session域中
                            request.getSession().setAttribute("user", user);
                        } else {

                            //不准确， do nothing
                        }

                    }
                }

            }

        }

        // 放行
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
