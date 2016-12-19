package com.explore.wang.web.back;

import com.explore.wang.domain.Product;
import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 商品列表的查询 servlet ， 把查询的商品信息保存到Request中
 *
 * Created by 王兆琦  on 2016/12/11 23.07.
 */
@WebServlet(name = "BackProdListServlet" , urlPatterns = "/BackProdListServlet")
public class BackProdListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //利用工厂的到service
        ProductService productService = CommonFactory.getCommonFactory().getInstance(ProductService.class);

        //查询到所有商品
        List<Product> productList = productService.findAll();

        //保存到域中
        request.setAttribute("productList", productList);

        //转发到
        request.getRequestDispatcher("/back/prod_list.jsp").forward(request, response);
    }
}
