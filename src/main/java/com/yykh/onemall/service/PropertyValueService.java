package com.yykh.onemall.service;

import com.yykh.onemall.mapper.PropertyValueMapper;
import com.yykh.onemall.pojo.Product;
import com.yykh.onemall.pojo.Property;
import com.yykh.onemall.pojo.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */

@Service
public class PropertyValueService  {

    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired PropertyService propertyService;

    public void update(PropertyValue bean) {
        propertyValueMapper.save(bean);
    }

    public void init(Product product) {
        List<Property> propertys= propertyService.listByCategory(product.getCategory());
        for (Property property: propertys) {
            PropertyValue propertyValue = getByPropertyAndProduct(product, property);
            if(null==propertyValue){
                propertyValue = new PropertyValue();
                propertyValue.setPid(product.getId());
                propertyValue.setPtid(property.getId());
                propertyValueMapper.save(propertyValue);
            }
        }
    }

    public PropertyValue getByPropertyAndProduct(Product product, Property property) {
        return propertyValueMapper.getByPropertyAndProduct(property.getId(),product.getId());
    }

    public List<PropertyValue> list(Product product) {
        return propertyValueMapper.findByProductOrderByIdDesc(product);
    }

}
