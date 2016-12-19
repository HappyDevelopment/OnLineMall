package com.explore.wang.domain;

import java.util.Date;

/**
 * order 实体类
 * Created by 王兆琦  on 2016/12/15 10.37.
 */
public class Order {

    private String id;          // uuid

    private double money;
    private String receiverinfo;

    private int paystate;   //0：表示未支付，1已支付
    private Date ordertime;
    private int user_id;    // 外键为 User中 id


    public double getMoney() {
        return money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getPaystate() {
        return paystate;
    }

    public void setPaystate(int paystate) {
        this.paystate = paystate;
    }

    public String getReceiverinfo() {
        return receiverinfo;
    }

    public void setReceiverinfo(String receiverinfo) {
        this.receiverinfo = receiverinfo;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


}
