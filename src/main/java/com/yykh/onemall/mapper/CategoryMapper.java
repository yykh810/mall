package com.yykh.onemall.mapper;

import com.github.pagehelper.Page;
import com.yykh.onemall.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CategoryMapper {

    @Select("select * from category ")
    Page<Category> findAll();

    @Select("select * from category ")
    List<Category> findAllAndGetList();

    @Insert("INSERT INTO category(name) VALUES (#{name})")
    void save(Category bean);

    @Update("update category set name=#{name} where id =#{id}")
    void update(Category bean);

    @Delete("delete from category where id =#{id}")
    void delete(int id);

    @Select("select * from category where id =#{id}")
    Category findOne(int id);
}
