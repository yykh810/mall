package com.yykh.onemall.mapper;

import com.github.pagehelper.Page;
import com.yykh.onemall.pojo.Category;
import com.yykh.onemall.pojo.Property;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface PropertyMapper {

    @Insert("insert into property (name,cid) values(#{name},#{category.id})")
    void save(Property bean);

    @Update("update property set name= #{name} where id= #{id}")
    void update(Property bean);


    Page<Property> findAll(Category category);

    @Delete("delete from property where id =#{id}")
    void delete(int id);


    Property findOne(int id);

    @Select("select * from property where cid=#{cid}")
    List<Property> getByCategory();
}
