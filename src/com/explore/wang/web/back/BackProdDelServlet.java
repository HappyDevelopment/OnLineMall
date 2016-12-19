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
 *
 * 商品删除的servlet
 * 不让使用者真正删除商品， 那么可以在删除时，可以让表增加一个列，列名是isDelete ,
 *      删除时，让这个列值赋值为1，  查询时，查询0
 *      防止误删
 * Created by 王兆琦  on 2016/12/12 09.20.
 */
@WebServlet(name = "BackProdDelServlet",urlPatterns = "/BackProdDelServlet")
public class BackProdDelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//1、接收参数
        String id = request.getParameter("id");
        //2、声明并使用工厂类创建商品业务层的对象
        ProductService prodService = CommonFactory.getCommonFactory().
                getInstance(ProductService.class);
        //3、调用删除的方法
        boolean flag = prodService.deleteProdById(id);
        //4、提示与转发
        if(flag){
            response.getWriter().write("删除成功！");
        }else{
            response.getWriter().write("删除失败");
        }
        response.setHeader("Refresh", "3;url="+request.getContextPath()
                +"BackProdListServlet");
    }
}
