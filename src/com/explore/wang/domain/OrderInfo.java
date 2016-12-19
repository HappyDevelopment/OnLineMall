package com.explore.wang.domain;

import java.util.Map;

/**
 * 订单详情domain
 * Created by 王兆琦  on 2016/12/16 20.32.
 */
public class OrderInfo {

    private Order order;

    // 没有商品的name 、 imgurl 、 price 等Product表的信息  （冗余字段）
    // 那么查询订单的时候，  price等信息怎么获取？？  当前价格和买的时候，价格是不一样的 ，
    //      要保存当时买的价格等信息。

    //额， 就是表设计的时候，加上冗余字段， 保存这些信息， 但是，现在就先保存到map中把
    private Map<Product, Integer> map ;


    public Map<Product, Integer> getMap() {
        return map;
    }

    public void setMap(Map<Product, Integer> map) {
        this.map = map;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


}
