package com.explore.wang.web.back;

import com.explore.wang.domain.SaleInfo;
import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.OrderService;
import com.explore.wang.utils.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 报表下载
 * Created by 王兆琦  on 2016/12/18 22.56.
 */
@WebServlet(name = "DownloadSalesServlet" ,urlPatterns = "/DownloadSalesServlet")
public class DownloadSalesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 调用业务查询所有的商品， 遍历 组成  **.csv  让浏览器下载

        OrderService orderService = CommonFactory.getCommonFactory().getInstance(OrderService.class);

        List<SaleInfo> list = orderService.findSaleInfos();

        StringBuffer stringBuffer = new StringBuffer("商品id,商品名称,销售总量\r\n");
        //2、组成csv格式的数据
        for(SaleInfo si: list){
            stringBuffer.append(si.getProd_id()+","+si.getProd_name()+","+si.getSale_num()+"\r\n");
        }

        //提供下载
        String fileName = "EasyMall销售榜单_" + DateUtil.getTime() + ".csv";

        // 利用流的形式
        response.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(fileName, "utf-8"));

        //乱码问题解决
        response.setContentType("text/html;charset=gbk");

        response.getWriter().write(stringBuffer.toString());

    }
}
