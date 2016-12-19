package com.explore.wang.domain;

import java.util.List;

/**
 * 分页显示的domain 类   封装所有需要的参数
 *
 * page 类型需要传入一个泛型对象，来进行封装数据
 * Created by 王兆琦  on 2016/12/12 10.25.
 */
public class Page<T> {

    //所有商品信息集合
    private List<T> list;
    //当前页
    private int currentPage;
    //总页数
    private int totalPage;
    //查询出来的总记录数
    private int totalRow;
    //每页的商品数量
    private int pageProdNum;

    // 封装 上一页与下一页
    private int nextPage ;
    private int prePage ;

    //索要查询的关键字
    private String name ;
    private String category;
    private Double minPrice;
    private Double maxPrice;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getPageProdNum() {
        return pageProdNum;
    }

    public void setPageProdNum(int pageProdNum) {
        this.pageProdNum = pageProdNum;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
