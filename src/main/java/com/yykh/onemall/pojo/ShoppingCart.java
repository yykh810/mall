package com.yykh.onemall.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */

@Data
public class ShoppingCart {

    List<OrderItem> orderItems;
    float total;

    public ShoppingCart(){
        orderItems = new ArrayList<OrderItem>();
    }

    public ShoppingCart(List<OrderItem> orderItems,float total){
        this.setOrderItems(orderItems);
        this.setTotal(total);
    }
}
