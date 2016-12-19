package com.explore.wang.web;

import com.explore.wang.domain.Product;
import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 购物车处理的逻辑
 * Created by 王兆琦  on 2016/12/14 20.31.
 */
@WebServlet(name = "CartAddServlet" , urlPatterns = "/CartAddServlet")
public class CartAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取参数
        String id = request.getParameter("pid");

        //调用查询
        ProductService productService = CommonFactory.getCommonFactory().getInstance(ProductService.class);

        Product product = productService.findProdById(id);

        //保存到Session域中的map中
        Map<Product,Integer> map = (Map<Product, Integer>) request.getSession().getAttribute("cart");

        //保存购买数值， 注意map的保存覆盖
        int buyNum = map.containsKey(product) ? map.get(product) + 1 : 1;

        map.put(product, buyNum);

        //重定向到cart页面
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }
}
