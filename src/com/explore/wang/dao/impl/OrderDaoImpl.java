package com.explore.wang.dao.impl;

import com.explore.wang.dao.OrderDao;
import com.explore.wang.domain.Order;
import com.explore.wang.domain.OrderItem;
import com.explore.wang.domain.SaleInfo;
import com.explore.wang.utils.BeanHandler;
import com.explore.wang.utils.BeanListHandler;
import com.explore.wang.utils.JdbcUtils;

import java.util.List;

/**
 * 订单实现类
 * Created by 王兆琦  on 2016/12/15 15.01.
 */
 public class OrderDaoImpl implements OrderDao {
    @Override
    public void addOrder(Order order) {

        String sql = "insert into orders(id,money,receiverinfo,paystate,ordertime,user_id)" +
                " values(?,?,?,?,?,?)";
        JdbcUtils.update(sql, order.getId(), order.getMoney(), order.getReceiverinfo(), order.getPaystate(),
                order.getOrdertime(), order.getUser_id());
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        String sql = "insert into orderitem(product_id,order_id,buynum) values(?,?,?)";
        JdbcUtils.update(sql, orderItem.getProduct_id(), orderItem.getOrder_id(), orderItem.getBuynum());
    }

    @Override
    public List<Order> findOrdersByUid(int uid) {
        String sql = "select * from orders where user_id = ?";

        return JdbcUtils.query(sql, new BeanListHandler<Order>(Order.class), uid);

    }


    @Override
    public List<OrderItem> findOrderItemByUid(int uid) {

        String sql = "select orderitem.* from orders, orderitem where orders.id = orderitem.order_id and " +
                " orders.user_id = ?";


        return JdbcUtils.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), uid);
    }

    @Override
    public Order findOrderByOid(String oId) {
        String sql = "select * from orders where id = ?";

        return JdbcUtils.query(sql, new BeanHandler<Order>(Order.class), oId);
    }

    @Override
    public List<OrderItem> findOrderItemsByOid(String oId) {
        String sql = "select * from orderitem where order_id = ?";

        return JdbcUtils.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), oId);
    }

    @Override
    public void delOrderItem(String oId) {
        String sql = "delete from orderitem where order_id = ?";

        JdbcUtils.update(sql, oId);
    }

    @Override
    public void delOrder(String oId) {
        String sql = "delete from orders where id = ?";

        JdbcUtils.update(sql, oId);
    }

    @Override
    public List<SaleInfo> findSaleInfos() {


        // 数据库字段与表字段一一对应‘211111
        String sql = " select p.id  prod_id, p.name prod_name , sum(oi.buynum) sale_num" +
                " from products p , orderitem oi ,orders o" +
                " where p.id = oi.product_id" +
                " and o.id = oi.order_id" +
                " and o.paystate = 1" +
                " GROUP BY p.id" +
                " ORDER BY sale_num DESC ";
        return JdbcUtils.query(sql, new BeanListHandler<SaleInfo>(SaleInfo.class));
    }

    @Override
    public void updatePaystate(String orderId, int i) {

        String sql = " update orders set paystate = ? where id = ?  ";

        JdbcUtils.update(sql, i, orderId);
    }

    @Override
    public Order findOrderByOidForUpdate(String orderId) {
        String sql = "select * from orders where id = ? for update";

        return JdbcUtils.query(sql, new BeanHandler<Order>(Order.class), orderId);
    }
}
