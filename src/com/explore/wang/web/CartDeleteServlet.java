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
 * 商品删除的servlet
 * Created by 王兆琦  on 2016/12/14 21.14.
 */
@WebServlet(name = "CartDeleteServlet", urlPatterns = "/CartDeleteServlet")
public class CartDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1、获取id
        String id = request.getParameter("id");
        //2、从session中获取cart
        Map<Product, Integer> cart = (Map<Product, Integer>)
                request.getSession().getAttribute("cart");
        //3、根据id创建key
        Product prod = new Product();
        prod.setId(id);

        //4从Map中删除
        cart.remove(prod);
        //5、跳转
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }
}
