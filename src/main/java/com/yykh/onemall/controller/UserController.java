package com.yykh.onemall.controller;

import com.github.pagehelper.PageInfo;
import com.yykh.onemall.pojo.User;
import com.yykh.onemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：yykh
 * @Descripton:
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public PageInfo<User> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        PageInfo<User> page = userService.list(start,size,5);
        return page;
    }

}
