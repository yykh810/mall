package com.yykh.onemall.controller;


import com.github.pagehelper.PageInfo;
import com.yykh.onemall.pojo.Product;
import com.yykh.onemall.pojo.ProductImage;
import com.yykh.onemall.service.CategoryService;
import com.yykh.onemall.service.ProductImageService;
import com.yykh.onemall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;

    @GetMapping("/categories/{cid}/products")
    public PageInfo<Product> list(@PathVariable("cid") int cid, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        PageInfo<Product> page =productService.list(cid, start, size,5 );
        productImageService.setFirstProductImages(page.getList());
        return page;
    }

    @GetMapping("/products/{id}")
    public Product get(@PathVariable("id") int id) throws Exception {
        Product bean=productService.get(id);
        return bean;
    }

    @PostMapping("/products")
    public Object add(@RequestBody Product bean) throws Exception {
        bean.setCreateDate(new Date());
        productService.add(bean);
        return bean;
    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        productService.delete(id);
        return null;
    }

    @PutMapping("/products")
    public Object update(@RequestBody Product bean) throws Exception {
        productService.update(bean);
        return bean;
    }
}
