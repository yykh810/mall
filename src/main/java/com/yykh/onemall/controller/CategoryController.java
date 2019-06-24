package com.yykh.onemall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykh.onemall.pojo.Category;
import com.yykh.onemall.service.CategoryService;
import com.yykh.onemall.utils.ImageUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public PageInfo<Category> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {

        start = start<0?0:start;
        PageInfo<Category> page = categoryService.list(start, size,5);
        return page;
    }

    @PostMapping("/categories")
    public Object add(Category bean, MultipartFile image, HttpServletRequest request) throws Exception {
        categoryService.add(bean);
        saveOrUpdateImageFile(bean, image, request);
        return bean;
    }

    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request)
            throws IOException {
        File file= new File(request.getServletContext().getRealPath("img/category")+bean.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        categoryService.delete(id);
        File  file= new File(request.getServletContext().getRealPath("img/category")+id+".jpg");
        file.delete();
        return null;
    }

    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id) throws Exception {
        Category bean=categoryService.get(id);
        return bean;
    }

    @PutMapping("/categories/{id}")
    public Object update(Category bean, MultipartFile image,HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        bean.setName(name);
        categoryService.update(bean);

        if(image!=null) {
            saveOrUpdateImageFile(bean, image, request);
        }
        return bean;
    }

}
