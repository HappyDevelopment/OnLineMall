package com.explore.wang.web;

import com.explore.wang.domain.Order;
import com.explore.wang.domain.OrderItem;
import com.explore.wang.domain.Product;
import com.explore.wang.domain.User;
import com.explore.wang.exception.MsgException;
import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 添加订单的处理控制层
 * Created by 王兆琦  on 2016/12/15 10.49.
 */
@WebServlet(name = "AddOrderServlet",urlPatterns = "/AddOrderServlet")
public class AddOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1 判断用户有没有登录
        Object obj = request.getSession().getAttribute("user");

        // 还是老方法， 如果没有登录 ， 跳转到登录界面
        if (obj == null || request.getSession(false) == null) {
            response.getWriter().write("<font style='color:red;font-size: 30px'>不好意思， 您没有登录， 请前去登录......</font>");

            response.sendRedirect(request.getContextPath() + "/login.jsp");

            // 此处虽然重定向了，但是还会继续执行。
            //不让执行下面的逻辑，
            return;
        }

        // 2 创建 Order 对象， 并赋值
        Order order = new Order();
        User user = (User) obj;

        double money = 0.0 ;  //暂时获取不到money
        order.setId(UUID.randomUUID().toString());
        order.setReceiverinfo(request.getParameter("receiverinfo"));
        order.setPaystate(0);  // 默认未支付
        order.setOrdertime(new Date());

        // 保存 user id 作为他的外键
        order.setUser_id(user.getId());

        // 3 从 session 域中获取每个订单项 OrderItem
        // 3.1 创建集合对象， 保存订单项
        List<OrderItem> list = new ArrayList<OrderItem>();
        // 3.2 从session中获取cart
        Map<Product, Integer> cartMap = (Map<Product, Integer>) request.getSession().getAttribute("cart");

        // 3.3  遍历得到Product 信息
        for (Map.Entry<Product, Integer> entryset : cartMap.entrySet()) {

            //  老问题了，  list要添加不同的对象元素
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder_id(order.getId());
            orderItem.setProduct_id(entryset.getKey().getId());
            orderItem.setBuynum(entryset.getValue());

            // 每次订单项的总价相加
            money = money + entryset.getKey().getPrice() * entryset.getValue();

            //加入到集合中， 是不同的订单项
            list.add(orderItem);
        }

        //设置总价
        order.setMoney(money);


        // 4 调用业务层处理
        OrderService orderService = CommonFactory.getCommonFactory().getInstance(OrderService.class);

        try {
            // 5  添加成功，
            orderService.addOrder(order, list);

            // 6 清空购物车
            cartMap.clear();

            // 6  提示订单添加成功， 并跳转
            response.getWriter().write("订单添加成功!");

            response.setHeader("Refresh","3;url="+request.getContextPath()+"/OrderListServlet");

        } catch (MsgException e) {

            //转发打订单页面，   可能是没有库存了  ，  设置消息
            request.setAttribute("msg",e.getMessage());

            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
