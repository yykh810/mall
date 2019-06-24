package com.yykh.onemall.mapper;

import com.github.pagehelper.Page;
import com.yykh.onemall.pojo.Order;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * @Author：yykh
 * @Descripton:
 */

public interface OrderMapper {

    @Select("select * from order_ ")
    Page<Order> findAll();

    @Select("select * from order_ where id= #{oid}")
    Order findOne(int oid);

    @Update("update order_ set orderCode=#{orderCode},total=#{total},totalNumber=#{totalNumber},address=#{address},post=#{post},receiver=#{receiver},mobile=#{mobile},userMessage=#{userMessage}," +
            "createDate=#{createDate},payDate=#{payDate},deliveryDate=#{deliveryDate},confirmDate=#{confirmDate},status=#{status},statusDesc=#{statusDesc}" +
            "where id=#{id}")
    void save(Order bean);
}
