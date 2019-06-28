package com.yykh.onemall.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：yykh
 * @Descripton:
 */


@Data
public class OrderItem {
    private int id;
    private Product product;
    private int oid;
    private int uid;
    private int number;

    public OrderItem(User user,Product product,int number){
        this.setProduct(product);
        this.setNumber(number);
        this.setUid(user.getId());
    }

    public OrderItem(){
        this.setProduct(new Product());
    }
}
