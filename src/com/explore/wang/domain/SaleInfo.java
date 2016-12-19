package com.explore.wang.domain;

/**
 * 销售查询列表的实体类
 * Created by 王兆琦  on 2016/12/18 22.36.
 */
public class SaleInfo {

    private String prod_id;
    private String prod_name;
    private int sale_num;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public int getSale_num() {
        return sale_num;
    }

    public void setSale_num(int sale_num) {
        this.sale_num = sale_num;
    }
}
