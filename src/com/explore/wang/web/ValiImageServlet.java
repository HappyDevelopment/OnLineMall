package com.explore.wang.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码
 * Created by 王兆琦  on 2016/11/29 18.41.
 */
@WebServlet(name = "ValiImageServlet", urlPatterns = "/ValiImageServlet")
public class ValiImageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //控制浏览器不要缓存验证码图片
        response.setDateHeader("Expires", -1);
        response.setHeader("Cache-control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        VerifyCode vc = new VerifyCode();

        //从内存中输出图片到客户端
        vc.drawImage(response.getOutputStream());


        // 把 验证码存到 session 中 ，以后校验
        String valistr = vc.getCode();
        request.getSession().setAttribute("valistr", valistr);

    }
}
