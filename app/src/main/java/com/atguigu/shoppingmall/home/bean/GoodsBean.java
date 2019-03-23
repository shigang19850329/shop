package com.atguigu.shoppingmall.home.bean;

import java.io.Serializable;

/**
 * @作者 石刚
 * QQ 342532640
 * @版本 v1.0
 * 商品对象
 * 对象要传递，必须实现序列化。
 */
public class GoodsBean implements Serializable {
    /**
     * 价格
     */
    private String cover_price;
    /**
     * 图片地址
     */
    private String figure;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品Id
     */
    private String product_id;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * 商品是否被选中，默认为true
     */
    private boolean isSelected = true;

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    /**
     * 产品数量，购物车中使用，默认为1;
     */
    private int Number = 1;

    public String getCover_price() {
        return cover_price;
    }

    public void setCover_price(String cover_price) {
        this.cover_price = cover_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "cover_price='" + cover_price + '\'' +
                ", figure='" + figure + '\'' +
                ", name='" + name + '\'' +
                ", product_id='" + product_id + '\'' +
                ", isSelected=" + isSelected +
                ", Number=" + Number +
                '}';
    }
}
