package com.yykh.onemall.controller;

import com.yykh.onemall.pojo.Product;
import com.yykh.onemall.pojo.PropertyValue;
import com.yykh.onemall.service.ProductService;
import com.yykh.onemall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */
@RestController
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;

    @GetMapping("/products/{pid}/propertyValues")
    public List<PropertyValue> list(@PathVariable("pid") int pid) throws Exception {
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValue> propertyValues = propertyValueService.list(product);
        return propertyValues;
    }

    @PutMapping("/propertyValues")
    public Object update(@RequestBody PropertyValue bean) throws Exception {
        propertyValueService.update(bean);
        return bean;
    }

}
