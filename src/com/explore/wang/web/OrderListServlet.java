package com.explore.wang.web;

import com.explore.wang.domain.OrderInfo;
import com.explore.wang.domain.User;
import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 订单详情页 ， 要进行付款了
 * Created by 王兆琦  on 2016/12/15 15.11.
 */
@WebServlet(name = "OrderListServlet" , urlPatterns = "/OrderListServlet")
public class OrderListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 登录没
        User user = (User) request.getSession().getAttribute("user");

        if (request.getSession(false) == null || user == null) {

            //没有登录， 重定向到登录
            response.sendRedirect(request.getContextPath() + "/login.jsp");

            //不让执行下面操作
            return;
        }

        // 业务层对象
        OrderService orderService = CommonFactory.getCommonFactory().getInstance(OrderService.class);


        //知道了 user的 id  可以查询购买的订单 , 装到list集合
        List<OrderInfo> list = orderService.findAllOrderByUid(user.getId());


        //得到了所有的 订单， 那么保存到域
        request.setAttribute("ordersList", list);


        //转发
        request.getRequestDispatcher("/orderList.jsp").forward(request,response);
    }
}
