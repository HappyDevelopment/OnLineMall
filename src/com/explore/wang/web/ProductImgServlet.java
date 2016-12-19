package com.explore.wang.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理商品咋web-inf目录下jsp页面不能正常显示的问题
 * Created by 王兆琦  on 2016/12/12 09.11.
 */
@WebServlet(name = "ProductImgServlet" , urlPatterns = "/ProductImgServlet")
public class ProductImgServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String imgurl = request.getParameter("imgurl");

        // 利用请求转发来显示图片
        request.getRequestDispatcher(imgurl).forward(request,response);
    }
}
