package com.explore.wang.listener;

/**
 * Session监听 ，
 * session中保存一个 map 对象， 保存用户购买的商品与数量
 * Created by 王兆琦  on 2016/12/14 20.41.
 */

import com.explore.wang.domain.Product;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

@WebListener()
public class CartSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        httpSessionEvent.getSession().setAttribute("cart", new HashMap<Product, Integer>());

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().removeAttribute("cart");

    }
}
