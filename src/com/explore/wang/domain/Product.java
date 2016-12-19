package com.explore.wang.domain;

/**
 * ProductDao domain类
 * Created by 王兆琦  on 2016/12/9 10.26.
 */
public class Product {

    private String id;
    private String name;
    private double price;
    private String category;
    private int pnum;
    private String imgurl;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // 堆内存中地址相同

        //非空判断  与 同属于Product类
        if (o == null || getClass() != o.getClass())
            return false;

        Product otherPoduct = (Product) o;

        //防止本类中的id为空 ，  这个也是需要考虑的因素
        if (id == null) {
            if (otherPoduct.id != null) {
                return false;
            }
        }else {
            return id.equals(otherPoduct.id);
        }

        return true;

    }

    @Override
    public int hashCode() {
        return 31 + (id == null ? 0 : id.hashCode());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
