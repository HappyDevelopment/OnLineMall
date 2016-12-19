package com.explore.wang.service.impl;

import com.explore.wang.dao.OrderDao;
import com.explore.wang.dao.ProductDao;
import com.explore.wang.domain.Order;
import com.explore.wang.domain.OrderItem;
import com.explore.wang.domain.Product;
import com.explore.wang.exception.MsgException;
import com.explore.wang.factory.CommonFactory;
import com.explore.wang.utils.JdbcUtils;

import java.sql.Connection;
import java.util.List;

/**
 * 订单业务层实现类
 * 一般的写法，  之后要控制耦合， 再进行处理
 * Created by 王兆琦  on 2016/12/15 14.59.
 */
public class OrderServiceImplBak  {

    //产生dao访问层
    ProductDao productDao = CommonFactory.getCommonFactory().getInstance(ProductDao.class);

    OrderDao orderDao = CommonFactory.getCommonFactory().getInstance(OrderDao.class);


    /**
     * 提交订单是可能有错误的， 可能订单中库存不足， 但是订单表已经添加了 ， 有了这条记录
     * 那么对数据库的操作要回滚
     *
     * 那么就涉及到了  数据库的很重要的操作  -----  事务
     *
     * @param order 订单相关的信息对象
     * @param list  订单项的list集合
     */
    public void addOrder(Order order, List<OrderItem> list) {

        Connection connection = null ;
        try {
            connection = JdbcUtils.getConnection();

            //禁止自动提交  ----  开启事务
            connection.setAutoCommit(false);

            //向 Order表中添加一个订单
            orderDao.addOrder(order);

            // 遍历订单项， 查看其中有没有什么问题
            for (OrderItem orderItem : list) {

                //判断库存是否充足
                Product product = productDao.findProdById(orderItem.getProduct_id());

                if (product.getPnum() < orderItem.getBuynum()) {
                    throw new MsgException("商品id:" + product.getId() + ",商品名为:" + product.getName() + "库存不足！");
                }
            }


        } catch (MsgException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
