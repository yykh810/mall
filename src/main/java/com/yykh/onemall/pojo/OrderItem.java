package com.yykh.onemall.pojo;

import lombok.Data;

/**
 * @Author：yykh
 * @Descripton:
 */

@Data
public class OrderItem {
    private int id;
    private Product prouduct;
    private int oid;
    private int uid;
    private int number;
}
