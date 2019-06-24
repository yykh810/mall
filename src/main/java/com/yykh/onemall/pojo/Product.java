package com.yykh.onemall.pojo;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Product {
    int id;
    private Category category;
    private String name;
    private String subTitle;
    private float originalPrice;
    private float promotePrice;
    private int stock;
    private Date createDate;
    private ProductImage firstProductImage;
    private List<ProductImage> productSingleImages;
    private List<ProductImage> productDetailImages;
    private int saleCount;
    private int reviewCount;
}
