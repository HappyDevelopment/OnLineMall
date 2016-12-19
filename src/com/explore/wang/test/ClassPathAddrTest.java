package com.explore.wang.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by 王兆琦  on 2016/12/5 17.50.
 */
@WebServlet(name = "ClassPathAddrTest", urlPatterns = "/ClassPathAddrTest")
public class ClassPathAddrTest extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        String o = request.getContextPath();
//        System.out.println(o);

        String path = request.getServletContext().getRealPath("WEB-INF/classes/config.properties");
        System.out.println(path);
        File file = new File(path);
        System.out.println(file.exists());

//        String path = ClassPathAddrTest.class.getClassLoader().getResource("c3p0-config.xml").getPath();
//        InputStream inputStream = new FileInputStream(new File(path));
//        properties.load(inputStream);


//        InputStream inputStream = ClassPathAddrTest.class.getResourceAsStream("c3p0-config.xml");
//        System.out.println(inputStream);

//        Properties properties = new Properties();
//
//        InputStream inputStream = ClassPathAddrTest.class.getClassLoader().getResourceAsStream("c3p0-config.xml");
//        properties.load(ClassPathAddrTest.class.getClassLoader().getResourceAsStream("c3p0-config.xml"));
//
//        System.out.println(properties);
//        System.out.println(properties.getProperty("user"));

        //资源关闭问题

    }
}
