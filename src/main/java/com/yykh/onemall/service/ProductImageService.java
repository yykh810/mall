package com.yykh.onemall.service;

import com.github.pagehelper.PageInfo;
import com.yykh.onemall.mapper.ProductImageMapper;
import com.yykh.onemall.pojo.Product;
import com.yykh.onemall.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：yykh
 * @Descripton:产品图片Service
 */

@Service
public class ProductImageService {
    public static final String TYPE_SINGLE = "single";
    public static final String TYPE_DETAIL = "detail";

    @Autowired
    ProductImageMapper productImageMapper;
    @Autowired
    ProductService productService;


    public List<ProductImage> listSingleProductImages(int pid) {
        return productImageMapper.getSingleProductImages(pid);
    }

    public List<ProductImage> listDetailProductImages(int pid) {
        return productImageMapper.getDetailProductImages(pid);
    }


    public void add(ProductImage bean) {
        productImageMapper.add(bean);
    }

    public ProductImage get(int id) {
        return productImageMapper.get(id);
    }

    public void delete(int id) {
        productImageMapper.delete(id);
    }

    public void setFirstProductImage(Product product) {
        List<ProductImage> singleImages = listSingleProductImages(product.getId());
        if(!singleImages.isEmpty())
            product.setFirstProductImage(singleImages.get(0));
        else
            product.setFirstProductImage(new ProductImage()); //这样做是考虑到产品还没有来得及设置图片，但是在订单后台管理里查看订单项的对应产品图片。

    }
    public void setFirstProductImages(List<Product> products) {
        for (Product product : products)
            setFirstProductImage(product);
    }

    public void setFirstProductImages(PageInfo<Product> products) {
        for (Product product : products.getList())
            setFirstProductImage(product);
    }


}
