package com.explore.wang.filter;

import com.explore.wang.domain.User;
import com.explore.wang.exception.MsgException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 检测用户有么有权限
 * Created by 王兆琦  on 2016/12/19 18.33.
 */
@WebFilter(filterName = "PrivilegeFilter" , urlPatterns = "/*")
public class PrivilegeFilter implements Filter {
    //保存user.txt文件中url
    private List<String> userList= new ArrayList<String>();
    //保存admin.txt文件中url
    private List<String> adminList = new ArrayList<String>();

    public void init(FilterConfig config) throws ServletException {

        // 初始化 读取 。txt 查看URL
        String line = "";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(
                    config.getServletContext().getRealPath("/WEB-INF/user.txt")))) ;

            while (( line = bufferedReader.readLine()) != null) {
                userList.add(line);
            }

            //  读取admin
            bufferedReader = new BufferedReader(new FileReader(new File(
                    config.getServletContext().getRealPath("/WEB-INF/admin.txt"))));

            while ((line = bufferedReader.readLine()) != null) {
                adminList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //进行 向下转型
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 得到当前要访问的 URI   只是资源历经名字 就可以啦 ， 因为文件保存的是资源路径
        String uri = request.getRequestURI();

        // 判断是否需要权限
        if (userList.contains(uri) || adminList.contains(uri)) {

            // 需要权限
            //查看登录没
            if (request.getSession(false) == null || request.getSession().getAttribute("user") == null) {
                //没有登录
                response.getWriter().write("当前资源需要权限控制，请您先登录！");
                response.setHeader("Refresh", "3;url=" + request.getContextPath() + "/login.jsp");
            } else {
                //登录了， 查看要访问的资源是哪个里面的
                User user = (User) request.getSession().getAttribute("user");
                if (userList.contains(uri) && "user".equals(user.getRole())) {
                    chain.doFilter(request, response);
                } else if (adminList.contains(uri) && "admin".equals(user.getRole())) {
                    chain.doFilter(request, response);
                } else {
                    throw new MsgException("权限不足，无法访问！");
                }

            }
        }

        chain.doFilter(req, resp);
    }

    public void destroy() {
    }

}
