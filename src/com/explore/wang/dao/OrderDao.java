package com.explore.wang.dao;

import com.explore.wang.domain.Order;
import com.explore.wang.domain.OrderItem;
import com.explore.wang.domain.SaleInfo;

import java.util.List;

/**
 *  订单数据库访问层
 * Created by 王兆琦  on 2016/12/15 15.00.
 */
public interface OrderDao extends Dao{

    /**
     * 向Order表中添加订单
     * @param order  订单详情对象
     */
    void addOrder(Order order);


    /**
     * 添加订单项
     * @param orderItem   订单项
     */
    void addOrderItem(OrderItem orderItem);

    /**
     * 根据用户id查找
     * @param id   用户id
     * @return
     */
    List<Order> findOrdersByUid(int id);



    /**
     * 根据用户id查找订单项
     * @param id    用户id
     * @return      订单项
     */
    List<OrderItem> findOrderItemByUid(int id);

    /**
     *  根据订单id删除订单
     * @param delId 订单id
     * @return  订单对象
     */
    Order findOrderByOid(String delId);

    /**
     *  根据订单id查找OrderItem 集合
     * @param oId  订单id
     * @return      OrderItem 集合
     */
    List<OrderItem> findOrderItemsByOid(String oId);

    /**
     * 根据订单id删除订单项
     * @param oId   订单id
     */
    void delOrderItem(String oId);
    /**
     * 根据订单id删除订单
     * @param oId   订单id
     */
    void delOrder(String oId);

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
    void updatePaystate(String orderId, int i);

    /**
     * 采用悲观锁来查询订单
     * @param orderId   id
     * @return  Order对象
     */
    Order findOrderByOidForUpdate(String orderId);
}
