package com.explore.wang.web.back;

import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  异步交互来实现更改商品的存货
 * Created by 王兆琦  on 2016/12/12 19.45.
 */
@WebServlet(name = "AjaxChangePnumServlet" ,urlPatterns = "/AjaxChangePnumServlet")
public class AjaxChangePnumServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取请求参数
        String id = request.getParameter("id");
        int newNum = Integer.parseInt(request.getParameter("newPnum"));

        //利用service进行处理查询
        ProductService productService = CommonFactory.getCommonFactory().getInstance(ProductService.class);

        boolean result = productService.updatePnum(id, newNum);
        response.getWriter().write(result+"");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
