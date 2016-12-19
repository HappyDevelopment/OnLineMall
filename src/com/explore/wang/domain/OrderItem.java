package com.explore.wang.domain;

/**
 * 因为每个订单还有不同的订单项， 那么把这些订单项分离出来，作为Order的外键
 *  这样处理的好处是耦合低， 表与表好处理关系
 *
 * Created by 王兆琦  on 2016/12/15 10.40.
 */
public class OrderItem {
    private String order_id;        //外键为 Order 中 id
    private String product_id;      //商品id作为他的外键
    private int buynum;             // 购买数量


    // 没有商品的name 、 imgurl 、 price 等Product表的信息  （冗余字段）
    // 那么查询订单的时候，  price等信息怎么获取？？  当前价格和买的时候，价格是不一样的 ，
    //      要保存当时买的价格等信息。

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getBuynum() {
        return buynum;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }

}
