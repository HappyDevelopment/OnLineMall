package com.explore.wang.service.impl;

import com.explore.wang.dao.OrderDao;
import com.explore.wang.dao.ProductDao;
import com.explore.wang.domain.*;
import com.explore.wang.exception.MsgException;
import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.OrderService;

import java.util.*;

/**
 * 订单业务层实现类
 * Created by 王兆琦  on 2016/12/15 14.59.
 */
public class OrderServiceImpl implements OrderService {

    //产生dao访问层
    ProductDao productDao = CommonFactory.getCommonFactory().getInstance(ProductDao.class);

    OrderDao orderDao = CommonFactory.getCommonFactory().getInstance(OrderDao.class);


    /**
     * 添加订单
     *
     * @param order 订单相关的信息对象
     * @param list  订单项的list集合
     */
    @Override
    public void addOrder(Order order, List<OrderItem> list) {

        try {
            //向Order中刚添加订单
            orderDao.addOrder(order);

            //检验订单项
            for (OrderItem orderItem : list) {

                Product product = productDao.findProdById(orderItem.getProduct_id());

                if (product.getPnum() < orderItem.getBuynum()) {
                    throw new MsgException("商品id:" + product.getId() + ",商品名为:" + product.getName() + "库存不足！");
                }

                // 可以的话， 添加订单条码
                orderDao.addOrderItem(orderItem);
                //修改库存
                productDao.updatePnum(product.getId(), product.getPnum() - orderItem.getBuynum());

            }


        } catch (MsgException e) {
            //上一级有接受异常的
            throw e;
        } catch (Exception e) {
            e.printStackTrace();    //  值打印， 不抛出了
        }
    }

    @Override
    public List<OrderInfo> findAllOrderByUid(int id) {

        // 来吧， 快活啊~~~

        //需要返回 list<orderInfo>对象  ， 那么
        List<OrderInfo> orderInfoList = new ArrayList<>();

        //根据用户id查询出所有订单
        List<Order> orderList = orderDao.findOrdersByUid(id);

        //根据用户id查询出所有的订单项
        List<OrderItem> orderItemList = orderDao.findOrderItemByUid(id);

        // 时刻记得 null 值 问题
        if (orderList != null) {

            //进行Ordernfo的设置
            for (Order order : orderList) {
                // 创建订单详情类
                OrderInfo orderInfo = new OrderInfo();
                // 保存订单
                orderInfo.setOrder(order);

                // 进行 Map<Product,Integer>的设置 ，
                // map对象放在外面，保存一个map对象，map中保存商品和购买数量信息

                // 找到 每个Order中的 OrderItem
                Map<Product, Integer> map = new HashMap<>();
                for (OrderItem orderItem : orderItemList) {


                    if (order.getId().equals(orderItem.getOrder_id())) {
                        //找到了 , 封装 Product对象
                        Product product = productDao.findProdById(orderItem.getProduct_id());

                        //保存到Map中
                        map.put(product, orderItem.getBuynum());
                    }
                }


                // 放在for中， 每一次循环就要添加，这样是不对的，要添加最终的map集合结果
                orderInfo.setMap(map);

                // 全部装到要返回的容器中
                orderInfoList.add(orderInfo);
            }
        }
        return orderInfoList;

    }

    @Override
    public void delOrder(String oId) {

        //额 ， 规定只有未支付的订单才可以删除
        Order order = orderDao.findOrderByOid(oId);

        if (order.getPaystate() != 0) {

            throw new MsgException("只有未支付的订单，才可以被删除");
        }

        // 2 删除之前， 还原库存

        // 找到要删除订单的订单项
        List<OrderItem> orderItemList = orderDao.findOrderItemsByOid(oId);

        if (orderItemList != null) {

            for (OrderItem orderItem : orderItemList) {

                // 根据订单项中的商品id来还原库存
                productDao.updatePnumAdd(orderItem.getProduct_id(), orderItem.getBuynum());
            }

            //删除订单项
            orderDao.delOrderItem(oId);
        }

        // 删除订单
        orderDao.delOrder(oId);
    }

    @Override
    public Order findOrderById(String orderid) {

        return orderDao.findOrderByOid(orderid);

    }

    @Override
    public List<SaleInfo> findSaleInfos() {
        return orderDao.findSaleInfos();
    }


    // 此处开启了事务，更新状态是一个事务级别的，肯还要去通知发货， 而且防止更新丢失问题
    @Override
    public void updatePaystate(String orderId, int i) {

        //这是悲观锁的解决办法 ， 不过会一致占用当前记录的操作锁定。 别人访问不了， 在高并发下不推荐

        // 推荐 乐观锁 是先查询出当前的版本号 oldV，
        //      然后在进行 update  where version = oldV , 有异常， 回滚。

        // 先 用悲观锁 查询出当前订单的状态 , 恩 ， 直接返回Order对象 好了，便于以后操作。 没有返回一个int的
        Order order = orderDao.findOrderByOidForUpdate(orderId);

        if (order.getPaystate() == 0) {

            orderDao.updatePaystate(orderId, i);
        } else {
            try {
                throw new Exception("订单不能重复更改");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
