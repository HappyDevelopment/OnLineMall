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

/**
 * 页面详情的展示
 * Created by 王兆琦  on 2016/12/14 19.55.
 */
@WebServlet(name = "ProductInfoServlet",urlPatterns = "/ProductInfoServlet")
public class ProductInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1 获取参数
        String id = request.getParameter("pid");

        //2 调用业务层进行查询
        ProductService productService = CommonFactory.getCommonFactory().getInstance(ProductService.class);

        Product product = productService.findProdById(id);

        //3 , 保存到域中
        request.setAttribute("product",product);
        //转发到jsp页面
        request.getRequestDispatcher("/prod_info.jsp").forward(request, response);


    }
}
