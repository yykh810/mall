package com.yykh.onemall.mapper;

import com.yykh.onemall.pojo.ProductImage;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */

public interface ProductImageMapper {

    @Select("select id from productimage where pid =#{pid} and type = 'single'")
    List<ProductImage> getSingleProductImages(int pid);

    @Select("select id from productimage where pid =#{pid} and type = 'detail'")
    List<ProductImage> getDetailProductImages(int pid);

    @Options(useGeneratedKeys = true)
    @Insert("insert into productimage (pid,type) values(#{pid},#{type})")
    void add(ProductImage bean);

    @Select("select * from productimage where id =#{id}")
    ProductImage get(int id);

    @Delete("delete from productimage where id = #{id}")
    void delete(int id);
}
