package com.yykh.onemall.mapper;

import com.yykh.onemall.pojo.Product;
import com.yykh.onemall.pojo.Review;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */
public interface ReviewMapper {


    List<Review> findByProductOrderByIdDesc(Product product);

    @Select("select count(*) from review where pid= #{id}")
    int countByProduct(Product product);

    @Insert("insert into review(content,uid,pid,createDate) values(#{content},#{user.id},#{product.id},#{createDate})")
    void save(Review review);
}
