package com.explore.wang.dao.impl;

import com.explore.wang.dao.ProductDao;
import com.explore.wang.domain.Product;
import com.explore.wang.utils.BeanHandler;
import com.explore.wang.utils.BeanListHandler;
import com.explore.wang.utils.JdbcUtils;
import com.explore.wang.utils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.explore.wang.utils.JdbcUtils.update;

/**
 * 添加商品类
 * Created by 王兆琦  on 2016/12/11 22.15.
 */
public class ProductDaoImpl implements ProductDao {
    @Override
    public void addProd(Product prod) {

        //sql 语句 写上id列名， 可以让我们写的时候好区别一些
        //
        String sql = "insert into products (id , name , price , category , pnum , imgurl ," +
                "description) values(?,?,?,?,?,?,?);";

        update(sql, prod.getId(), prod.getName(), prod.getPrice(), prod.getCategory(),
                prod.getPnum(), prod.getImgurl(), prod.getDescription());

    }

    @Override
    public List<Product> findAll() {

        String sql = "select * from products";
        return JdbcUtils.query(sql, new BeanListHandler<Product>(Product.class));
    }

    @Override
    public boolean updatePnum(String id, int newNum) {

        String sql = "update products set pnum = ? where id = ?";
        int resulr = JdbcUtils.update(sql, newNum, id);
        return resulr > 0;
    }

    @Override
    public boolean deleteProdById(String id) {

        String sql = "delete  from products where id = ?";

        int result = JdbcUtils.update(sql, id);

        return result > 0;
    }

    @Override
    public int getProdByKey(String name, String category, double minPrice, double maxPrice) {

        // 根据条件模糊查询
        String sql = "select count(*)" +
                " from products where name like ? and category like ?" +
                " and price >= ? and price <= ? ";

        return JdbcUtils.query(sql, new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet resultSet) throws SQLException {

                //不出意外只有一条结果集 , 让指针指向下一位
                resultSet.next();
                int result = resultSet.getInt(1);
                return result;
            }
        }, "%" + name + "%", "%" + category + "%", minPrice, maxPrice);


    }

    @Override
    public List<Product> findProdsByKeyLimit(int begin, int pageProdNum,
                                             String name, String category, double minPrice, double maxPrice) {

        String sql = "select * from products where name like ? and category like ? and price >= ? and price <= ? limit ?,?";

        return JdbcUtils.query(sql, new BeanListHandler<Product>(Product.class),
                "%" + name + "%", "%" + category + "%", minPrice, maxPrice, begin, pageProdNum);
    }

    @Override
    public Product findProdById(String id) {
        String sql = "select * from products where id = ?";

        return JdbcUtils.query(sql, new BeanHandler<Product>(Product.class), id);
    }

    @Override
    public void updatePnumAdd(String product_id, int buynum) {

        String sql = " update products set pnum = pnum + ? where id  = ?";

        //不 return 了 ， 直接执行吧
        JdbcUtils.update(sql, buynum, product_id);
    }

}
