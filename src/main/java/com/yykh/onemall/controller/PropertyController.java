package com.yykh.onemall.controller;


import com.github.pagehelper.PageInfo;
import com.yykh.onemall.pojo.Property;
import com.yykh.onemall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping("/categories/{cid}/properties")
    public PageInfo<Property> list(@PathVariable("cid") int cid, @RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size)throws Exception{
        PageInfo<Property> list = propertyService.list(cid, start, size, 5);
        return list;
    }

    @GetMapping("/properties/{id}")
    public Property get(@PathVariable("id") int id) throws Exception {
        Property bean=propertyService.get(id);
        return bean;
    }


    @PostMapping("/properties")
    public Object add(@RequestBody Property bean) throws Exception {
        propertyService.add(bean);
        return bean;
    }

    @DeleteMapping("/properties/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        propertyService.delete(id);
        return null;
    }

    @PutMapping("/properties")
    public Object update(@RequestBody Property bean) throws Exception {
        propertyService.update(bean);
        return bean;
    }





}
