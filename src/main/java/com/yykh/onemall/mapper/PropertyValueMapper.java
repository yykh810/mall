package com.yykh.onemall.mapper;

import com.yykh.onemall.pojo.Product;
import com.yykh.onemall.pojo.PropertyValue;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */


public interface PropertyValueMapper {

    @Update("update propertyvalue set pid=#{pid},ptid=#{ptid},value=#{value} where id=#{id}")
    void save(PropertyValue bean);

    @Select("select * from propertyvalue where ptid=#{ptid},pid=#{pid}")
    PropertyValue getByPropertyAndProduct(int ptid, int pid);

    @Select("select * from propertyvalue where pid = #{id} order by id desc")
    List<PropertyValue> findByProductOrderByIdDesc(Product product);
}
