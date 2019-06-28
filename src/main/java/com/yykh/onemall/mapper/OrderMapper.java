package com.yykh.onemall.mapper;

import com.github.pagehelper.Page;
import com.yykh.onemall.pojo.Order;
import com.yykh.onemall.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */

public interface OrderMapper {

    @Select("select * from order_ ")
    Page<Order> findAll();

    @Select("select * from order_ where id= #{oid}")
    Order findOne(int oid);


    @Update("update order_ set orderCode=#{orderCode},address=#{address},post=#{post},receiver=#{receiver},mobile=#{mobile},userMessage=#{userMessage}," +
            "createDate=#{createDate},payDate=#{payDate},deliveryDate=#{deliveryDate},confirmDate=#{confirmDate},status=#{status}" +
            "where id=#{id}")
    void save(Order bean);


    @Options(useGeneratedKeys = true)
    @Insert("Insert into order_(uid,orderCode,address,post,receiver,mobile,userMessage,createDate,payDate,deliveryDate,confirmDate,status) values(#{uid},#{orderCode},#{address},#{post},#{receiver},#{mobile},#{userMessage}," +
            "#{createDate},#{payDate},#{deliveryDate},#{confirmDate},#{status})")
    void add(Order bean);

    @Select("select * from order_ where  uid=#{user.id} and status != #{status}")
    public List<Order> findByUserAndReverseStatusOrderByIdDesc(@Param("user") User user,@Param("status") String status);
}
