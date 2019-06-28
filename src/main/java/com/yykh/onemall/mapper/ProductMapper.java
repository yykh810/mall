package com.yykh.onemall.mapper;

import com.github.pagehelper.Page;
import com.yykh.onemall.pojo.Category;
import com.yykh.onemall.pojo.DTO.CategoryWithProducts;
import com.yykh.onemall.pojo.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface ProductMapper {

    @Insert("insert into product(name,subTitle,originalPrice,promotePrice,stock,createDate,cid) values(#{name},#{subTitle},#{originalPrice},#{promotePrice},#{stock},#{createDate},#{category.id}) ")
    void save(Product bean);

    @Delete("delete from product where id =#{id}")
    void delete(int id);

    @Update("update product set name =#{name},subTitle=#{subTitle}, originalPrice=#{originalPrice},promotePrice=#{promotePrice},stock=#{stock} where id =#{id} ")
    void update(Product bean);

    Product findOne(int id);

    Page<Product> findAll(Category category);

    void fill(Category category);


    List<Product> findByCategoryOrderById(Category category);


    List<Product> listProductByCategory(Category category);


    Page<Product> findByNameLike(String s);
}
