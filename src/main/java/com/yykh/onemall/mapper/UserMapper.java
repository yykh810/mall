package com.yykh.onemall.mapper;

import com.github.pagehelper.Page;
import com.yykh.onemall.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author：yykh
 * @Descripton:
 */

public interface UserMapper {

    @Select("select * from user ")
    Page<User> findAll();

    @Select("select * from user where name =#{name} ")
    User findByName(String name);

    @Insert("insert into user(name,password,salt) values(#{name},#{password},#{salt})")
    void save(User user);

    @Select("select * from user where name =#{name} and password=#{password}")
    User getByNameAndPassword(@Param("name")String name, @Param("password")String password);
}
