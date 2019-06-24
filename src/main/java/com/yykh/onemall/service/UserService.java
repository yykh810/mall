package com.yykh.onemall.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykh.onemall.mapper.UserMapper;
import com.yykh.onemall.pojo.Category;
import com.yykh.onemall.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：yykh
 * @Descripton:
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public PageInfo<User> list(int start, int size, int navigatePages) {

        PageHelper.startPage(start,size);
        Page<User> cs =userMapper.findAll();
        PageInfo<User> page = new PageInfo<>(cs);
        return page;
    }

    public boolean isExist(String name) {
        User user = getByName(name);
        return null!=user;
    }

    public User getByName(String name) {
        return userMapper.findByName(name);
    }

    public void add(User user) {
        userMapper.save(user);
    }

    public User get( String name, String password) {
        return userMapper.getByNameAndPassword(name,password);
    }


}