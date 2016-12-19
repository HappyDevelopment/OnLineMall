package com.explore.wang.web;

import com.explore.wang.domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 修改购物车数量
 * Created by 王兆琦  on 2016/12/14 22.45.
 */
@WebServlet(name = "UpdateCartServlet" ,urlPatterns = "/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String id = request.getParameter("id");
        int newBuyNum = Integer.parseInt(request.getParameter("newBuyNum"));

//        //调用业务层
//        ProductService productService = CommonFactory.getCommonFactory().getInstance(ProductService.class);
//  只是更改session中的map数量，没有真正提交

        Map<Product,Integer> map = (Map<Product, Integer>) request.getSession().getAttribute("cart");

        // 重新进行赋值
        Product product = new Product();
        product.setId(id);

        map.put(product, newBuyNum);

        //4、使用重定向， 来达到页面跳转， URL地址改变
        response.sendRedirect(request.getContextPath()+"/cart.jsp");

    }
}
