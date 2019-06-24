package com.yykh.onemall.Comparator;

import com.yykh.onemall.pojo.Product;

import java.util.Comparator;

/**
 * @Author：yykh
 * @Descripton:
 */
public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return (int) (p1.getPromotePrice()-p2.getPromotePrice());
    }

}
