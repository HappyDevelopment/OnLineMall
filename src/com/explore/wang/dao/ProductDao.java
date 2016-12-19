package com.explore.wang.dao;

import com.explore.wang.domain.Product;

import java.util.List;

/**
 * 查询商品信息的共有接口
 * Created by 王兆琦  on 2016/12/11 22.11.
 */
public interface ProductDao extends Dao{


    /**
     * 添加商品
     * @param product  商品类信息
     */
    public void addProd(Product product) ;


    /**
     * 查询所有的商品信息
     * @return  封装为list集合的商品信息
     */
    public List<Product> findAll();


    /**
     * 更改商品数量
     * @param id   id
     * @param newNum    新数量
     * @return  true为成功了
     */
    boolean updatePnum(String id, int newNum);

    /**
     * 根据指定id进行商品的删除
     * @param id  id
     * @return  true出删除了
     */
    boolean deleteProdById(String id);

    /**
     * 根据查询条件，查询符合条件商品个数
     * @param name：名称关键字
     * @param category：分类关键字
     * @param minPrice：价格区间的最小值
     * @param maxPrice：价格区间的最大值
     * @return 查询符合条件商品个数
     */
    int getProdByKey(String name, String category, double minPrice, double maxPrice);

    /**
     * 带有关键字与价格区间的分页查询
     * @param currentPageIndex  查询当前页的起始最表
     * @param pageProdNum  每页显示的行数
     * @param name          商品名称关键字
     * @param category      分类关键字
     * @param minPrice      价格区间的最小值
     * @param maxPrice      价格区间的最大值
     * @return      封装了分页相关信息的Page<Product> 对象
     */
    List<Product> findProdsByKeyLimit(int currentPageIndex, int pageProdNum, String name, String category, double minPrice, double maxPrice);

    /**
     * 根据id查询
     * @param id    id值
     * @return  具体商品类
     */
    Product findProdById(String id);

    /**
     *  根据商品id来增加指定的数量
     * @param product_id    商品id
     * @param buynum    还原数量
     */
    void updatePnumAdd(String product_id, int buynum);
}
