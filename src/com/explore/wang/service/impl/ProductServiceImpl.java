package com.explore.wang.service.impl;

import com.explore.wang.dao.ProductDao;
import com.explore.wang.domain.Page;
import com.explore.wang.domain.Product;
import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.ProductService;

import java.util.List;

/**
 * 商品服务的实现类
 * Created by 王兆琦  on 2016/12/11 22.39.
 */
public class ProductServiceImpl implements ProductService{


    // 利用工厂类得到具体的类
    ProductDao productDao = CommonFactory.getCommonFactory().getInstance(ProductDao.class);


    public void addProd(Product prod) {
        productDao.addProd(prod);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public boolean updatePnum(String id, int newNum) {
        return productDao.updatePnum(id, newNum);
    }

    @Override
    public boolean deleteProdById(String id) {
        return productDao.deleteProdById(id);
    }

    @Override
    public Page<Product> pageList(int currentPage, int pageProdNum, String name, String category, double minPrice, double maxPrice) {

        // 终于到不是直接调用dao层返回的了，  这里需要自己在进行封装

        Page<Product> productPage = new Page<Product>();

        //设置属性值 ，要返回 这个productPage的
        productPage.setName(name.trim());      // 去除空格的小细节不要忘记了
        productPage.setCategory(category.trim());

        //有没有价格值
        if (minPrice != -1) {
            productPage.setMinPrice(minPrice);
        }
        if (maxPrice != Double.MAX_VALUE) {
            productPage.setMaxPrice(maxPrice);
        }

        //分页相关属性值
        productPage.setCurrentPage(currentPage);
        productPage.setPageProdNum(pageProdNum);

        //总行数
        int totalRow = productDao.getProdByKey(name, category, minPrice, maxPrice);
        productPage.setTotalRow(totalRow);

        //总页数
        int totalPage = (int) Math.ceil(totalRow * 1.0 / pageProdNum);
        productPage.setTotalPage(totalPage);

        //上一页
        productPage.setPrePage(currentPage == 1 ? 1 : currentPage - 1);
        //下一页
        productPage.setNextPage(currentPage == totalPage ? totalPage : currentPage + 1);


        //当前页的所有商品信息,
        List<Product> list = productDao.findProdsByKeyLimit((currentPage - 1) * pageProdNum,
                pageProdNum, name.trim(), category.trim(), minPrice, maxPrice);

        productPage.setList(list);

        return productPage;
    }

    @Override
    public Product findProdById(String id) {
        return productDao.findProdById(id);
    }
}
