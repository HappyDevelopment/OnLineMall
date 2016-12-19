package com.explore.wang.web;

import com.explore.wang.domain.User;
import com.explore.wang.exception.NullDataException;
import com.explore.wang.exception.RegistException;
import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.UserService;
import com.explore.wang.utils.MD5Utils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注册校验
 * Created by 王兆琦  on 2016/11/28 19.07.
 */
@WebServlet(name = "RegistServlet", urlPatterns = "/RegistServlet")
public class RegistServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1设置编码一致  ,  有过滤器了
        // 2 首先进行验证码的校验，防止不是表单提交的信息
        String valistr = request.getParameter("valistr");
        String valistrTrue = (String) request.getSession().getAttribute("valistr");
        try {
            if (valistr == null || !valistrTrue.equalsIgnoreCase(valistr)) {
                //出错
                request.setAttribute("msg", "验证码不正确");
                // 请求转发8
                request.getRequestDispatcher("/regist.jsp").forward(request, response);

                // 不用接下来的判断
                return;
            }


            //3 不获取数据了， 利用BeanUtils封装为对象
            // 第一个参数是要封装对象引用
            // request.getParameterMap() 可以获取所有的请求参数组成的map , 而第二个参数就是要map
            // 要求getParameter()中的值与bean中属性值一致 ， 底层是set+'大写'。。。
            User user = new User();
            BeanUtils.populate(user, request.getParameterMap());


            //4校验数据
            user.checkData();

            //5没有错误的话注册用户 , 还必须校验用户名是否存在
            // 利用工厂加泛型加反射来传入接口字节码文件，来得到具体的实现类， 更加松耦合
            UserService userService = CommonFactory.getCommonFactory().getInstance(UserService.class);

            //现在需要 MD5 进行密码的加密
            user.setPassword(MD5Utils.md5(user.getPassword()));


            // 注册加密的USer
            userService.registUser(user);

            //6！！注册成功，跳转到首页
            response.getWriter().write("<font style='color:red;font-size: 30px'>恭喜您注册成功, 3秒之后跳转到首页......</font>");
            response.setHeader("Refresh", "3;url=" + request.getContextPath() + "index.jsp");


        } catch (NullDataException e) {

            // 得到错误的信息，返回到注册页面提示错误
            String msg = e.getMessage();
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/regist.jsp").forward(request, response);

            //返回节省判断
            return;
        } catch (RegistException e) {

            // 得到错误的信息，返回到注册页面提示错误
            String msg = e.getMessage();
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/regist.jsp").forward(request, response);

            //返回节省判断
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // 抛出最大的错误 ， sql 等
            throw new RuntimeException(e);
        }

    }
}
