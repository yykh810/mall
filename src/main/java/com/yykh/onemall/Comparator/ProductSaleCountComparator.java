package com.yykh.onemall.Comparator;

import com.yykh.onemall.pojo.Product;

import java.util.Comparator;

/**
 * @Author：yykh
 * @Descripton:
 */
public class ProductSaleCountComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return p2.getSaleCount()-p1.getSaleCount();
    }

}
