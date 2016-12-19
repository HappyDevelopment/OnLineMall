package com.explore.wang.web;

import com.explore.wang.exception.MsgException;
import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删除订单操作
 * Created by 王兆琦  on 2016/12/17 19.47.
 */
@WebServlet(name = "OrderDeleteServlet" , urlPatterns = "/OrderDeleteServlet")
public class OrderDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取请求
        String delId = request.getParameter("id");

        //调用业务
        OrderService orderService = CommonFactory.getCommonFactory().getInstance(OrderService.class);

        //删除订单
        try {
            orderService.delOrder(delId);
        } catch (MsgException e) {
            e.printStackTrace();

            //向页面输出错误原因
            response.getWriter().write(e.getMessage());
        }

        //重定向到
        response.sendRedirect(request.getContextPath() + "/OrderListServlet");
    }
}
