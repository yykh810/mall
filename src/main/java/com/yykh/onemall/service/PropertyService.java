package com.yykh.onemall.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykh.onemall.mapper.PropertyMapper;
import com.yykh.onemall.pojo.Category;
import com.yykh.onemall.pojo.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PropertyService {

    @Autowired
    PropertyMapper propertyMapper;
    @Autowired
    CategoryService categoryService;

    public void add(Property bean) {
        propertyMapper.save(bean);
    }

    public void delete(int id) {
        propertyMapper.delete(id);
    }

    public Property get(int id) {
        return propertyMapper.findOne(id);
    }

    public void update(Property bean) {
        propertyMapper.update(bean);
    }

    public PageInfo<Property> list(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.get(cid);
        PageHelper.startPage(start,size,"id");
        Page<Property> pr = propertyMapper.findAll(category);
        PageInfo<Property> page = new PageInfo<>(pr);
        return page;

    }

    public List<Property> listByCategory(Category category) {
        return propertyMapper.getByCategory();
    }
}
