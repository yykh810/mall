package com.yykh.onemall.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykh.onemall.mapper.CategoryMapper;
import com.yykh.onemall.pojo.Category;
import com.yykh.onemall.pojo.DTO.CategoryWithProducts;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ProductService productService;

    public PageInfo<Category> list(int start,int size,int navigatePages){
        PageHelper.startPage(start,size,"id ");
        Page<Category> cs=categoryMapper.findAll();
        PageInfo<Category> page = new PageInfo<>(cs);
        return page;
    }

    public List<CategoryWithProducts> list(){
        List<Category> list = categoryMapper.findAllAndGetList();
        int length =list.size();
        List<CategoryWithProducts> cwp =new ArrayList<CategoryWithProducts>(length);
        int num=0;
        for(Category i : list){
            cwp.add(num,new CategoryWithProducts());
            cwp.get(num).setCategory(i);
            num++;
        }
        return cwp;
    }



    public void add(Category bean) {
        categoryMapper.save(bean);
    }

    public void delete(int id) {
        categoryMapper.delete(id);
    }

    public Category get(int id) {
        Category c= categoryMapper.findOne(id);
        return c;
    }

    public void update(Category bean) {
        categoryMapper.update(bean);
    }
}
