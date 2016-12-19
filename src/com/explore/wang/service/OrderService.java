package com.explore.wang.service;

import com.explore.wang.anno.Trans;
import com.explore.wang.domain.Order;
import com.explore.wang.domain.OrderInfo;
import com.explore.wang.domain.OrderItem;
import com.explore.wang.domain.SaleInfo;

import java.util.List;

/**
 * 订单业务层接口
 * Created by 王兆琦  on 2016/12/15 14.59.
 */
public interface OrderService  extends Service{

    /**
     * 添加订单的方法
     * @param order 订单相关的信息对象
     * @param list  订单项的list集合
     */
    @Trans
    void addOrder(Order order, List<OrderItem> list);

    /**
     * 根据用户id查询出所有的订单
     * @param id      用户id
     * @return      封装信息 该用户所有的订单  List<OrderInfo>
     */
    List<OrderInfo> findAllOrderByUid(int id);

    /**
     * 根据订单id删除订单
     * @param oId 订单id
     */
    @Trans
    void delOrder(String oId);

    /**
     * 根据id查Order
     * @param orderid
     * @return  订单虚席
     */
    Order findOrderById(String orderid);

    /**
     * 查询出销售列表
     * @return List<SaleInfo>
     */
    List<SaleInfo> findSaleInfos();

    /**
     *  根据返回的数值， 来改变订单状态
     * @param orderId 订单id
     * @param i     支付结果
     */
    @Trans
    void updatePaystate(String orderId, int i);
}
