package com.yykh.onemall.Comparator;

import com.yykh.onemall.pojo.Product;

import java.util.Comparator;

/**
 * @Author：yykh
 * @Descripton:比较器，用于在商品页面按综合对商品进行排序时的比较。
 */

public class ProductAllComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount()*p2.getSaleCount()-p1.getReviewCount()*p1.getSaleCount();
    }

}
