package com.yykh.onemall.Comparator;

import com.yykh.onemall.pojo.Product;

import java.util.Comparator;

/**
 * @Author：yykh
 * @Descripton:
 */
public class ProductDateComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return p1.getCreateDate().compareTo(p2.getCreateDate());
    }

}
