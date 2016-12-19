package com.explore.wang.web.back;

import com.explore.wang.domain.SaleInfo;
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
 * 销售榜单的查询
 * Created by 王兆琦  on 2016/12/18 22.33.
 */
@WebServlet(name = "SaleInfoListServlet" ,urlPatterns = "/SaleInfoListServlet")
public class SaleInfoListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrderService orderService = CommonFactory.getCommonFactory().getInstance(OrderService.class);

        List<SaleInfo> saleInfoList = orderService.findSaleInfos();

        request.setAttribute("list", saleInfoList);

        request.getRequestDispatcher("/back/saleList.jsp").forward(request, response);

    }
}
