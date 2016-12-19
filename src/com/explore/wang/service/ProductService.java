package com.explore.wang.service;

import com.explore.wang.domain.Page;
import com.explore.wang.domain.Product;

import java.util.List;

/**
 * 商品的服务类    ---->   业务层对象
 * Created by 王兆琦  on 2016/12/11 22.39.
 */
public interface ProductService extends Service{

    /**添加商品
     * @param product：封装了商品信息的Product类的对象
     */
    public void addProd(Product product);

    /**查询所有商品
     * @return 所用商品的集合对象
     */
    public List<Product> findAll();


    /**
     * 更改商品数量
     * @param id  商品id
     * @param newNum    新的数量
     * @return  true 改好了
     */
    public boolean updatePnum(String id, int newNum);


    /**
     * 根据指定的id删除商品
     * @param id  id
     * @return  true为删除了
     */
    public boolean deleteProdById(String id);

    /**
     * 带有关键字与价格区间的分页查询
     * @param currentPage  当前页
     * @param pageProdNum  每页显示的行数
     * @param name          商品名称关键字
     * @param category      分类关键字
     * @param minPrice      价格区间的最小值
     * @param maxPrice      价格区间的最大值
     * @return      封装了分页相关信息的Page<Product> 对象
     */
    Page<Product> pageList(int currentPage, int pageProdNum, String name, String category, double minPrice, double maxPrice);

    /**
     * 根据id 查询商品
     * @param id id值
     * @return  具体商品类
     */
    Product findProdById(String id);
}
