package com.explore.wang.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by 王兆琦  on 2016/12/8 22.36.
 */
public class WebUrlListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //在web应用被加载， 这个监听器就被初始化了


        // 保存web应用的实际URL路径，  这样在web应用一加载就存到SerlvetContext域中
        servletContextEvent.getServletContext().setAttribute("app",
                servletContextEvent.getServletContext().getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        // web应用正常销毁 ， 执行这个方法 , 把保存的属性值移除
        servletContextEvent.getServletContext().removeAttribute("app");
    }
}
