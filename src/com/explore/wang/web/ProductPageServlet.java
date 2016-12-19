package com.explore.wang.web;

import com.explore.wang.domain.Page;
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
 * 商品分页处理
 * Created by 王兆琦  on 2016/12/13 19.21.
 */
@WebServlet(name = "ProductPageServlet", urlPatterns = "/ProductPageServlet")
public class ProductPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获得请求参数 ， 这两个是可定能获得的
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int pageProdNum = Integer.parseInt(request.getParameter("pageProdNum"));

        // 四个查询条件的默认值
        String name = "";
        String category = "";
        double minPrice = -1;
        double maxPrice = Double.MAX_VALUE;

        // 有值就赋值
        if (request.getParameter("name") != null) {
            name = request.getParameter("name");
        }
        if (request.getParameter("category") != null) {
            category = request.getParameter("category");
        }

        //价格不为null 与 价格有数据的情况
        if (request.getParameter("minPrice") != null &&
                !"".equals(request.getParameter("minPrice"))) {
            minPrice = Double.parseDouble(request.getParameter("minPrice"));
        }
        String max = request.getParameter("maxprice");
        if (max != null && !"".equals(max)) {
            maxPrice = Double.parseDouble(max);
        }

        //调用业务层进行商品的查询
        ProductService productService = CommonFactory.getCommonFactory().getInstance(ProductService.class);

        Page<Product> pageList = productService.pageList
                (currentPage, pageProdNum, name, category, minPrice, maxPrice);

        //封装到域中名称
        request.setAttribute("pageList", pageList);

        //转发到prod_list.jsp
        request.getRequestDispatcher("/prod_list.jsp").forward(request, response);

    }
}
