package com.yykh.onemall.mapper;

import com.yykh.onemall.pojo.Order;
import com.yykh.onemall.pojo.OrderItem;
import com.yykh.onemall.pojo.Product;
import com.yykh.onemall.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */

public interface OrderItemMapper {


    ArrayList<OrderItem> findByOrderOrderByIdDesc(Order order);

    List<OrderItem> findByProduct(Product product);

    List<OrderItem> findByUserAndOrderIsNull(User user);
}
